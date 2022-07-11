package com.userservice.controller;

import com.userservice.config.JwtTokenUtil;
import com.userservice.dto.JwtRequest;
import com.userservice.dto.UserDto;
import com.userservice.service.JWTUserDetailsService;
import com.userservice.ui.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import lombok.extern.log4j.Log4j2;

/**
 * for authentication & validation
 *
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1.0/market/user")
@Log4j2
public class JwtAuthenticationController {

	private final AuthenticationManager authenticationManager;

	private final JwtTokenUtil jwtTokenUtil;

	private final JWTUserDetailsService userDetailsService;

	public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
			JWTUserDetailsService userDetailsService) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
		this.userDetailsService = userDetailsService;
	}

	/**
	 * authenticate user when trying to login
	 *
	 */
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		log.info("started createAuthenticationToken **");
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		var tokenOnly = token.substring(0, token.lastIndexOf('.') + 1);
		var expiration = ((Claims) Jwts.parser().parse(tokenOnly).getBody()).getExpiration();

		return ResponseEntity.ok(new JwtResponse(token,expiration));
	}

	/**
	 * internal authentication by authentication manager
	 *
	 */
	private void authenticate(String username, String password) throws Exception {
		log.info("started authenticate **");
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	/**
	 * find logged in user detail
	 *
	 */
	@GetMapping("/user/{userName}")
	public ResponseEntity<UserDto> getCurrentLoggedInUser(@PathVariable String userName) {
		log.info("started getCurrentLoggedInUser **");
		final UserDto userDto = userDetailsService.findUser(userName);

		return ResponseEntity.status(HttpStatus.OK).body(userDto);
	}
	
	/**
	 * register new user
	 * @param userDto -> Request object of user
	 * @return -> success message
	 */
	@PostMapping("/register")
	
	public ResponseEntity<String> registerNewUser(@RequestBody UserDto userDto) {
		log.info("started getCurrentLoggedInUser **");
		final String message = userDetailsService.registerUser(userDto);

		return ResponseEntity.status(HttpStatus.OK).body(message);
	}
}
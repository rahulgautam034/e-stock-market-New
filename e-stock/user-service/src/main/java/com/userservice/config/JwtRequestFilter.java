package com.userservice.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import com.userservice.service.JWTUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.log4j.Log4j2;

/**
 * validate and generate new token
 *
 */
@Component
@Log4j2
public class JwtRequestFilter extends OncePerRequestFilter {

	private final JWTUserDetailsService userService;

	private final JwtTokenUtil jwtTokenUtil;

	public JwtRequestFilter(final JWTUserDetailsService userService, final JwtTokenUtil jwtTokenUtil) {
		super();
		this.userService = userService;
		this.jwtTokenUtil = jwtTokenUtil;
	}


	/**
	 * filter bearer token
	 */
	@Override
	protected void doFilterInternal(@Context final HttpServletRequest request,final HttpServletResponse response,final FilterChain chain)
			throws ServletException, IOException {
		log.info("called doFilterInternal");
		final String tokenHeader = request.getHeader("Authorization");

		String username = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
			jwtToken = tokenHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				log.error("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				log.error("JWT Token has expired");
			}
		} else {
			log.info("JWT Token does not begin with Bearer String");
		}

		// Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			final UserDetails userDetails = this.userService.loadUserByUsername(username);

			// if token is valid configure Spring Security to manually set
			// authentication
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

				final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		chain.doFilter(request, response);
	}

}

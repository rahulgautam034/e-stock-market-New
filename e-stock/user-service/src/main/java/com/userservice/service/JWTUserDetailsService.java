package com.userservice.service;

import java.util.ArrayList;
import java.util.Optional;

import com.userservice.dto.UserDto;
import com.userservice.entity.AuthEntity;
import com.userservice.exception.StockException;
import com.userservice.repository.AuthRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import lombok.extern.log4j.Log4j2;

/**
 * JWTUserDetailsService get and set the users
 *
 */
@Service
@Log4j2
public class JWTUserDetailsService implements UserDetailsService {

	private final AuthRepository authRepository;

	private final ModelMapper modelMapper;

	public JWTUserDetailsService(final AuthRepository authRepository,final ModelMapper modelMapper) {
		this.authRepository = authRepository;
		this.modelMapper = modelMapper;
	}

	/**
	 * JWTUserDetailsService get and set the users
	 *
	 */
	@Override
	public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException {
		log.info("called loadUserByUsername");
		final Optional<AuthEntity> resp = authRepository.findByUserName(userName);
		if (resp.isPresent()) {
			final AuthEntity user = resp.get();
			return new User(user.getUserName(), user.getPassword(), new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + userName);
		}

	}

	/**
	 * bcryptPassword
	 *
	 */
	public static String bcryptPassword(final String password) {
		log.info("called bcryptPassword");
		final PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}

	/**
	 * find user from DB
	 *
	 */
	public UserDto findUser(final String userName) {
		log.info("called findUser");
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		final Optional<AuthEntity> authEntity = authRepository.findByUserName(userName);

		if(authEntity.isEmpty()) {
			throw new StockException("user not found");
		}
			return modelMapper.map(authEntity.get(), UserDto.class);
	}

	/**
	 * register new user
	 */
	public String registerUser(final UserDto userDto) {
		if (checkIfUserExist(userDto.getUserName())) {
			throw new StockException("User already exists for this userName");
		}
		final AuthEntity authEntity = new AuthEntity();
		authEntity.setFirstName(userDto.getFirstName());
		authEntity.setLastName(userDto.getLastName());
		authEntity.setRole("USER");
		authEntity.setUserName(userDto.getUserName());
		authEntity.setPassword(bcryptPassword(userDto.getPassword()));
		authRepository.save(authEntity);
		return "user Created Successfully";
	}

	/**
	 * check is user already registered
	 */
	public boolean checkIfUserExist(final String userName) {
		final Optional<AuthEntity> user =  authRepository.findByUserName(userName);
		return user.isPresent();
	}
}

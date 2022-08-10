package com.userservice.service;

import com.userservice.dto.AuthDTO;
import com.userservice.entity.AuthEntity;
import com.userservice.exception.StockException;
import com.userservice.repository.AuthRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * AuthServiceImpl for user validation
 *
 */
@Service
public class AuthServiceImpl implements AuthService {

	public final AuthRepository userRepository;

	public AuthServiceImpl(final AuthRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * validate the user
	 *
	 */
	@Override
	public AuthEntity validateUser(final AuthDTO authDTO) {
		final Optional<AuthEntity> resp = userRepository.findByUserName(authDTO.getUserName());

		if(resp.isEmpty()){
			throw new StockException("User not found in db");
		}
			final AuthEntity user = resp.get();
			return user.getPassword().equals(authDTO.getPassword()) ? user : null;
		}

}

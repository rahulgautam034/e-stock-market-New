package com.userservice.service;

import com.userservice.dto.AuthDTO;
import com.userservice.entity.AuthEntity;
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

	public AuthServiceImpl(AuthRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * validate the user
	 *
	 */
	@Override
	public AuthEntity validateUser(AuthDTO authDTO) {
		Optional<AuthEntity> resp = userRepository.findByUserName(authDTO.getUserName());

		if (resp.isPresent()) {
			AuthEntity user = resp.get();
			if (user.getPassword().equals(authDTO.getPassword())) {
				return user;
			} else {
				return null;
			}
		} else {
			return null;

		}
	}

}

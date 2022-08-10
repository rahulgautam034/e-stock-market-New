package com.userservice.service;

import com.userservice.dto.AuthDTO;
import com.userservice.entity.AuthEntity;

/**
 * authentication methods
 *
 */
public interface AuthService {

	AuthEntity validateUser(AuthDTO user);

}

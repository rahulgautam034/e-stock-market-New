package com.userservice.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * jwt token response modal 
 *
 */
@Getter
@Setter
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwtToken;

	private final Date expiration;

	public JwtResponse(String jwtToken,Date expiration) {
		this.jwtToken = jwtToken;
		this.expiration = expiration;
	}

	public String getToken() {
		return this.jwtToken;
	}
}

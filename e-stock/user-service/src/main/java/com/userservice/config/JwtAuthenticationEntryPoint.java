package com.userservice.config;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.NoArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;
@NoArgsConstructor
@Component
@Log4j2
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	private static final long serialVersionUID = -7858869558953243875L;

	@Override
	public void commence(final HttpServletRequest request,
						 final HttpServletResponse response,
						 final AuthenticationException authException) throws IOException {
		log.info("callled commence");

		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}
}
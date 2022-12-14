package com.userservice.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
@NoArgsConstructor
@Component
@Log4j2
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;

	public static final long TOKEN_VALIDITY = 5 * 60 * 60;

	@Value("${jwt.secret}")
	private String secret;

	/**
	 * retrieve username from jwt token
	 * 
	 * @param token -> user token
	 * @return -> claim
	 */
	public String getUsernameFromToken(final String token) {
		log.info("getUsernameFromToken");
		return getClaimFromToken(token, Claims::getSubject);
	}

	/**
	 * retrieve expiration date from jwt token
	 * 
	 * @param token -> user token
	 * @return -> expiy date of token
	 */
	public Date getExpirationDateFromToken(final String token) {
		log.info("getExpirationDateFromToken called");
		return getClaimFromToken(token, Claims::getExpiration);
	}

	/**
	 * retrieve get Claim from jwt token
	 * 
	 * @param token -> user token
	 * @return -> claim
	 */
	public <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
		log.info("getClaimFromToken called");
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * for retrieveing any information from token we will need the secret key
	 * 
	 * @param token -> user token
	 * @return -> all claims
	 */
	private Claims getAllClaimsFromToken(final String token) {
		log.info("getAllClaimsFromToken called");
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	/**
	 * check if the token has expired
	 * 
	 * @param token ->user token
	 * @return -> return true if tokenexpired otherwise false
	 */
	private Boolean isTokenExpired(final String token) {
		log.info("isTokenExpired called");
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	/**
	 * generate token for user
	 * 
	 * @param userDetails -> user username & password
	 * @return -> generated token
	 */
	public String generateToken(final UserDetails userDetails) {
		log.info("generateToken called");
		final Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	/**
	 * while creating the token - 1. Define claims of the token, like Issuer,
	 * Expiration, Subject, and the ID 2. Sign the JWT using the HS512 algorithm and
	 * secret key. 3. According to JWS Compact
	 * Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	 * compaction of the JWT to a URL-safe string
	 * 
	 * @param claims -> token claims
	 * @param subject -> token subject
	 * @return -> generated token
	 */
	private String doGenerateToken(final Map<String, Object> claims,final String subject) {
		log.info("doGenerateToken called");
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	/**
	 * validate token
	 * 
	 * @param token -> user token
	 * @param userDetails -> user username/password
	 * @return -> is token valid or not
	 */
	public Boolean validateToken(final String token,final UserDetails userDetails) {
		log.info("validateToken called");
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}

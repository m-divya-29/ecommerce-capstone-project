package com.divya29.paymentservice.services;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import com.divya29.paymentservice.exceptions.AuthenticationException;

public class JwtUtils {

	private JwtUtils() {
	}

	private static Map<String, Object> extractClaims(Authentication authentication) {
		if (authentication instanceof JwtAuthenticationToken) {
			JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
			return jwtToken.getTokenAttributes();
		} else {
			throw new AuthenticationException("Unauthorized: Invalid JWT token");
		}
	}

	public static Long extractUserIdClaim(Authentication authentication) {
		Map<String, Object> claims = JwtUtils.extractClaims(authentication);

		if (claims != null && claims.containsKey("userId")) {
			Object userIdClaim = claims.get("userId");
			if (userIdClaim instanceof Number) {
				return ((Number) userIdClaim).longValue();
			} else {
				throw new IllegalArgumentException("userId is not a valid number");
			}
		} else {
			throw new IllegalStateException("userId is missing");
		}

	}

	public static String extractTokenValue(Authentication authentication) {
		if (authentication instanceof JwtAuthenticationToken) {
			JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
			return jwtToken.getToken().getTokenValue();
		} else {
			throw new AuthenticationException("Unauthorized: Invalid JWT token");
		}
	}

}

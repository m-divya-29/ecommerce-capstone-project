package com.divya29.paymentservice.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class JWTClientUtils {

	@Value("${auth.server.token.url}")
	private String tokenUrl;

	@Value("${client.id}")
	private String clientId;

	@Value("${client.secret}")
	private String clientSecret;

	public String getAccessToken() {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setBasicAuth(clientId, clientSecret);

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "client_credentials");

		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

		ResponseEntity<Map<String, String>> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, entity,
				new ParameterizedTypeReference<Map<String, String>>() {
				});

		if (response.getStatusCode() == HttpStatus.OK) {
			Map<String, String> responseBody = response.getBody();
			return responseBody != null ? responseBody.get("access_token") : null;
		} else {
			throw new RuntimeException("Failed to retrieve access token");
		}

	}
}

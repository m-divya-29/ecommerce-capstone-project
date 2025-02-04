package com.divya29.orderservice.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.divya29.orderservice.dtos.CartDTO;
import com.divya29.orderservice.exceptions.NotFoundException;

@Service
public class CartServiceClient {
	private final RestTemplate restTemplate;
	private final RestTemplateBuilder restTemplateBuilder;
	private final String cartServiceUrl;

	public CartServiceClient(RestTemplateBuilder restTemplateBuilder,
			@Value("${cart.service.url}") String cartServiceUrl) {
		this.restTemplateBuilder = restTemplateBuilder;
		this.restTemplate = this.restTemplateBuilder.build();
		this.cartServiceUrl = cartServiceUrl;
	}

	public CartDTO getCart(Long userId, String token) throws NotFoundException {
		String url = cartServiceUrl + "/cart/user";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<CartDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, CartDTO.class);

		if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
			throw new NotFoundException("Cart not found for user with ID: " + userId);
		}

		CartDTO cartDTO = response.getBody();

		return cartDTO;
	}

	public void deleteCart(String token) {
		String url = cartServiceUrl + "/cart";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		HttpEntity<String> entity = new HttpEntity<>(headers);

		restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);

	}
}

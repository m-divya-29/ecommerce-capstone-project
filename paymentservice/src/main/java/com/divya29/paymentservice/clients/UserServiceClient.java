package com.divya29.paymentservice.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.divya29.paymentservice.dtos.User;

@Service
public class UserServiceClient implements IUserServiceClient {

	private final RestTemplate restTemplate;
	private final RestTemplateBuilder restTemplateBuilder;

	@Value("${user.service.url}")
	private String userServiceUrl;

	public UserServiceClient(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplateBuilder = restTemplateBuilder;
		this.restTemplate = this.restTemplateBuilder.build();
	}

	@Override
	public User getUserDetails(Long customerId) {
		String url = userServiceUrl + "/users/" + customerId.toString();
		return restTemplate.getForObject(url, User.class);
	}
}

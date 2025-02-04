package com.divya29.paymentservice.clients;

import com.divya29.paymentservice.dtos.User;

public interface IUserServiceClient {
	User getUserDetails(Long customerId);
}

package com.divya29.paymentservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;

import com.divya29.paymentservice.dtos.Order;
import com.divya29.paymentservice.models.Payment;

public interface IPaymentService {
	String createPaymentLink(Authentication authentication, Long orderId) throws Exception;

	Payment createPayment(Order order, String paymentLinkId, String paymentLinkUrl);

	Optional<Payment> getPaymentById(Long paymentId);

	Payment savePayment(Payment payment);

	List<Payment> getAllPayments();

	Optional<Payment> getPaymentByOrderId(Long orderId);

	Optional<Payment> getPaymentByReferenceId(String referenceId);
}

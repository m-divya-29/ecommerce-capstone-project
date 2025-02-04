package com.divya29.paymentservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.divya29.paymentservice.clients.IOrderServiceClient;
import com.divya29.paymentservice.dtos.Order;
import com.divya29.paymentservice.dtos.PaymentLinkResponse;
import com.divya29.paymentservice.exceptions.NotFoundException;
import com.divya29.paymentservice.models.Payment;
import com.divya29.paymentservice.models.PaymentStatus;
import com.divya29.paymentservice.paymentgateways.PaymentGateway;
import com.divya29.paymentservice.paymentgateways.PaymentGatewayChooserStrategy;
import com.divya29.paymentservice.repositories.PaymentRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentService implements IPaymentService {

	private PaymentGatewayChooserStrategy paymentGatewayChooserStrategy;

	private PaymentRepository paymentRepository;

	private final IOrderServiceClient orderServiceClient;

	@Override
	public String createPaymentLink(Authentication authentication, Long orderId) throws Exception {

		PaymentGateway paymentGateway = paymentGatewayChooserStrategy.getBestPaymentGateway();

		String token = JwtUtils.extractTokenValue(authentication);
		Order order = orderServiceClient.getOrderDetails(orderId, token);

		if (order == null) {
			throw new NotFoundException("Order not found with id " + orderId);
		}

		Long amount = Math.round(order.getTotalAmount() * 100);
		PaymentLinkResponse paymentLinkResponse = paymentGateway.generatePaymentLink(amount, orderId);

		Payment payment = createPayment(order, paymentLinkResponse.getPaymentLinkId(),
				paymentLinkResponse.getPaymentLinkUrl());

		orderServiceClient.updateOrderWithPaymentId(orderId, payment.getId(), token);

		return paymentLinkResponse.getPaymentLinkUrl();
	}

	@Override
	public Payment createPayment(Order order, String paymentLinkId, String paymentLinkUrl) {
		Payment payment = new Payment();
		payment.setOrderId(order.getId());
		payment.setAmount(order.getTotalAmount());
		payment.setPaymentStatus(PaymentStatus.PENDING);
		payment.setPaymentLink(paymentLinkUrl);
		payment.setReferenceId(paymentLinkId);
		return savePayment(payment);
	}

	@Override
	public Optional<Payment> getPaymentById(Long paymentId) {
		return paymentRepository.findById(paymentId);
	}

	@Override
	public Payment savePayment(Payment payment) {
		return paymentRepository.save(payment);
	}

	@Override
	public List<Payment> getAllPayments() {
		return paymentRepository.findAll();
	}

	@Override
	public Optional<Payment> getPaymentByOrderId(Long orderId) {
		return paymentRepository.findByOrderId(orderId);
	}

	@Override
	public Optional<Payment> getPaymentByReferenceId(String referenceId) {
		return paymentRepository.findByReferenceId(referenceId);
	}
}

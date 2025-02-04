package com.divya29.paymentservice.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.divya29.paymentservice.clients.IUserServiceClient;
import com.divya29.paymentservice.clients.OrderServiceClient;
import com.divya29.paymentservice.dtos.Order;
import com.divya29.paymentservice.dtos.User;
import com.divya29.paymentservice.exceptions.NotFoundException;
import com.divya29.paymentservice.models.Payment;
import com.divya29.paymentservice.models.PaymentStatus;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.RequestOptions;

@Service
public class StripeEventService {

	private final PaymentService paymentService;
	private final OrderServiceClient orderServiceClient;
	private final IUserServiceClient userServiceClient;
	private final EmailNotificationService emailNotificationService;
	private final String stripeSecretKey;
	private JWTClientUtils jwtClientUtils;

	public StripeEventService(PaymentService paymentService, OrderServiceClient orderServiceClient,
			IUserServiceClient userServiceClient, EmailNotificationService emailNotificationService,
			@Value("${stripe.secret_key}") String stripeSecretKey, JWTClientUtils jwtClientUtils) {
		this.paymentService = paymentService;
		this.orderServiceClient = orderServiceClient;
		this.userServiceClient = userServiceClient;
		this.emailNotificationService = emailNotificationService;
		this.stripeSecretKey = stripeSecretKey;
		this.jwtClientUtils = jwtClientUtils;
	}

	private PaymentIntent getPaymentIntent(Session session) throws StripeException {
		String paymentIntentId = session.getPaymentIntent();
		RequestOptions requestOptions = RequestOptions.builder().setApiKey(stripeSecretKey).build();
		PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId, requestOptions);
		return paymentIntent;

	}

	private void updatePaymentStatusFromStripe(Payment payment, String stripeStatus) {
		PaymentStatus paymentStatus = PaymentStatusMapper.mapStripeStatusToPaymentStatus(stripeStatus);
		payment.setPaymentStatus(paymentStatus);
		paymentService.savePayment(payment);
	}

	private void sendOrderStatusEmail(String customerEmail, String status, String orderId) {
		String subject = "Payment " + status + " - Order #" + orderId;
		String body = "Your payment for Order #" + orderId + " has " + status;
		String topic = "sendEmail";
		emailNotificationService.sendEmail(customerEmail, subject, body, topic);
	}

	public void handlePaymentReceived(Session session) throws Exception {
		String referenceId = session.getPaymentLink();
		if (referenceId == null) {
			throw new RuntimeException("Unable to get reference id");
		}

		Optional<Payment> optionalPayment = paymentService.getPaymentByReferenceId(referenceId);
		if (optionalPayment.isEmpty()) {
			throw new NotFoundException("Payment object with reference id " + referenceId + " not found");
		}
		Payment payment = optionalPayment.get();

		PaymentIntent paymentIntent = getPaymentIntent(session);
		String paymentIntentStatus = paymentIntent.getStatus();
		if (paymentIntentStatus == null) {
			throw new RuntimeException("Unable to get payment status ");
		}

		updatePaymentStatusFromStripe(payment, paymentIntentStatus);

		String token = jwtClientUtils.getAccessToken();
		Long orderId = payment.getOrderId();
		orderServiceClient.updateOrderStatus(orderId.toString(), payment.getPaymentStatus().toString(), token);

		Order order = orderServiceClient.getOrderDetails(orderId, token);
		if (order == null) {
			throw new RuntimeException("Unable to get order with id " + orderId);
		}

		Long customerId = order.getCustomerId();
		User customer = userServiceClient.getUserDetails(customerId);
		if (customer == null) {
			throw new RuntimeException("Unable to get customer with id " + customerId);
		}
		String customerEmail = customer.getEmail();
		System.out.println(customer);
		sendOrderStatusEmail(customerEmail, paymentIntentStatus, orderId.toString());

	}

}

package com.divya29.paymentservice.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divya29.paymentservice.dtos.CreatePaymentLinkRequestDto;
import com.divya29.paymentservice.models.Payment;
import com.divya29.paymentservice.services.PaymentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/pay")
@AllArgsConstructor
public class PaymentController {

	private PaymentService paymentService;

	@PostMapping("/initiate")
	public String createPaymentLink(Authentication authentication, @RequestBody CreatePaymentLinkRequestDto requestDto)
			throws Exception {
		return paymentService.createPaymentLink(authentication, requestDto.getOrderId());
	}

	@GetMapping
	public ResponseEntity<List<Payment>> getAllPayments() {
		List<Payment> payments = paymentService.getAllPayments();
		return ResponseEntity.ok(payments);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
		Optional<Payment> payment = paymentService.getPaymentById(id);
		return payment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/order/{orderId}")
	public ResponseEntity<Payment> getPaymentByOrderId(@PathVariable Long orderId) {
		Optional<Payment> payment = paymentService.getPaymentByOrderId(orderId);
		return payment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/reference/{referenceId}")
	public ResponseEntity<Payment> getPaymentByReferenceId(@PathVariable String referenceId) {
		Optional<Payment> payment = paymentService.getPaymentByReferenceId(referenceId);

		return payment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

}

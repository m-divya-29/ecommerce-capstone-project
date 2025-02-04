package com.divya29.paymentservice.dtos;

import com.divya29.paymentservice.models.Payment;
import com.divya29.paymentservice.models.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto {

	private Long paymentId;
	private Long orderId;
	private Double amount;
	private PaymentStatus status;

	public static PaymentResponseDto toDto(Payment payment) {
		if (payment == null) {
			return null;
		}

		PaymentResponseDto dto = new PaymentResponseDto();
		dto.setPaymentId(payment.getId());
		dto.setOrderId(payment.getOrderId());
		dto.setAmount(payment.getAmount());
		dto.setStatus(payment.getPaymentStatus());

		return dto;
	}

	public static Payment toEntity(PaymentResponseDto dto) {
		if (dto == null) {
			return null;
		}

		Payment payment = new Payment();
		payment.setId(dto.getPaymentId());
		payment.setOrderId(dto.getOrderId());
		payment.setAmount(dto.getAmount());
		payment.setPaymentStatus(dto.getStatus());

		return payment;
	}
}
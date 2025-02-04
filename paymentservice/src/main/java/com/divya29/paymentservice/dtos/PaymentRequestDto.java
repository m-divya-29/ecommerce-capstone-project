package com.divya29.paymentservice.dtos;

import com.divya29.paymentservice.models.Payment;
import com.divya29.paymentservice.models.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto {

	private Long orderId;
	private Double amount;
	private PaymentStatus paymentStatus;

	public Payment toPayment() {
		Payment payment = new Payment();
		payment.setOrderId(this.orderId);
		payment.setAmount(this.amount);
		payment.setPaymentStatus(this.paymentStatus);
		return payment;
	}

	public static PaymentRequestDto fromPayment(Payment payment) {
		return new PaymentRequestDto(payment.getOrderId(), payment.getAmount(), payment.getPaymentStatus());
	}
}

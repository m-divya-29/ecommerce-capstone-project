package com.divya29.paymentservice.paymentgateways;

import com.divya29.paymentservice.dtos.PaymentLinkResponse;

public interface PaymentGateway {

	PaymentLinkResponse generatePaymentLink(Long amount, Long orderId) throws Exception;
}

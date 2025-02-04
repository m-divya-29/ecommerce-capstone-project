package com.divya29.paymentservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaymentLinkResponse {
	private String paymentLinkUrl;
	private String paymentLinkId;
}

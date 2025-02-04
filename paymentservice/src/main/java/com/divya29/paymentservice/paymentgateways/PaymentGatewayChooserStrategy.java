package com.divya29.paymentservice.paymentgateways;

import org.springframework.stereotype.Service;

import com.divya29.paymentservice.paymentgateways.razorpay.RazorpayPaymentGateway;
import com.divya29.paymentservice.paymentgateways.stripe.StripePaymentGateway;

@Service
public class PaymentGatewayChooserStrategy {
	private RazorpayPaymentGateway razorpayPaymentGateway;
	private StripePaymentGateway stripePaymentGateway;

	public PaymentGatewayChooserStrategy(RazorpayPaymentGateway razorpayPaymentGateway,
			StripePaymentGateway stripePaymentGateway) {
		this.razorpayPaymentGateway = razorpayPaymentGateway;
		this.stripePaymentGateway = stripePaymentGateway;
	}

	public PaymentGateway getBestPaymentGateway() {
		return stripePaymentGateway;
	}

	public PaymentGateway getRazortPaymentGateway() {
		return razorpayPaymentGateway;
	}

}
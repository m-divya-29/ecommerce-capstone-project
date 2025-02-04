package com.divya29.paymentservice.controllers.webhooks;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divya29.paymentservice.services.StripeEventService;
import com.stripe.model.Event;
import com.stripe.model.PaymentLink;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/webhooks/stripe/")
public class StripeWebhookController {

	private final StripeEventService stripeEventService;

	@Value("${stripe.webhook.secret}")
	private String endpointSecret;

	public StripeWebhookController(StripeEventService stripeEventService) {
		this.stripeEventService = stripeEventService;
	}

	@PostMapping
	public void receiveWebhookEvent(HttpServletRequest request) throws Exception {
		System.out.println("Waiting");
		Event event = null;
		String body = request.getReader().lines().collect(Collectors.joining("\n"));
		try {
			String sigHeader = request.getHeader("Stripe-Signature");
			event = Webhook.constructEvent(body, sigHeader, endpointSecret);
		} catch (Exception e) {
			System.out.println("Error while verifying stripe event signature" + e);
			return;
		}

		switch (event.getType()) {
		case "payment_link.created":
			handlePaymentLinkCreated(event);
			break;
		case "payment_link.updated":
			handlePaymentLinkUpdated(event);
			break;
		case "checkout.session.completed":
			handlePaymentReceived(event);
			break;
		default:
			System.out.println("Unhandled event type: " + event.getType());
			break;
		}

	}

	private void handlePaymentLinkCreated(Event event) {
		PaymentLink paymentLink = (PaymentLink) event.getDataObjectDeserializer().getObject().orElse(null);
		if (paymentLink != null) {
			System.out.println("Payment link created: " + paymentLink.getId());
		}
	}

	private void handlePaymentLinkUpdated(Event event) {
		PaymentLink paymentLink = (PaymentLink) event.getDataObjectDeserializer().getObject().orElse(null);
		if (paymentLink != null) {
			System.out.println("Payment link updated: " + paymentLink.getId());
		}
	}

	private void handlePaymentReceived(Event event) throws Exception {
		Session session = (Session) event.getDataObjectDeserializer().getObject().orElse(null);

		if (session != null) {
			stripeEventService.handlePaymentReceived(session);
		}
	}
}
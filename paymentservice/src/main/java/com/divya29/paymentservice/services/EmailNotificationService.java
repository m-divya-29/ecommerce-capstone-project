package com.divya29.paymentservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.divya29.paymentservice.dtos.EmailNotification;

@Service
public class EmailNotificationService {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	private final String FROM_EMAIL = "support@ekart.com";

	public void sendEmail(String fromEmail, String recipientEmail, String subject, String body, String topic) {
		recipientEmail = "divya.gracie@gmail.com";
		EmailNotification emailNotification = new EmailNotification();
		emailNotification.setTo(recipientEmail);
		emailNotification.setFrom(fromEmail);
		emailNotification.setSubject(subject);
		emailNotification.setBody(body);

		kafkaTemplate.send(topic, emailNotification);
	}

	public void sendEmail(String recipientEmail, String subject, String body, String topic) {
		sendEmail(FROM_EMAIL, recipientEmail, subject, body, topic);
	}
}

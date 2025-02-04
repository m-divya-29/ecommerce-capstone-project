package com.divya29.notificationservice.consumers;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.divya29.notificationservice.dtos.SendEmailEventDto;
import com.divya29.notificationservice.utils.EmailUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SendEmailEventConsumer {
	private ObjectMapper objectMapper;

	@Value("${spring.mail.username}")
	private String email;

	@Value("${spring.mail.password}")
	private String password;

	@Value("${spring.mail.host}")
	private String host;

	@Value("${spring.mail.port}")
	private String port;

	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
	private String starttls;

	@Value("${spring.mail.properties.mail.smtp.auth}")
	private String auth;

	public SendEmailEventConsumer(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@KafkaListener(topics = "sendEmail", groupId = "emailService")
	public void handleSendEmailEvent(String message) throws JsonProcessingException {
		SendEmailEventDto event = objectMapper.readValue(message, SendEmailEventDto.class);

		String to = event.getTo();
		String body = event.getBody();
		String from = event.getFrom();
		String subject = event.getSubject();

		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", auth);
		props.put("mail.smtp.starttls.enable", starttls);

		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(email, password);
			}
		};
		Session session = Session.getInstance(props, auth);

		EmailUtil.sendEmail(session, from, to, subject, body);
	}
}

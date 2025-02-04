package com.divya29.notificationservice.controllers;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divya29.notificationservice.dtos.SendEmailEventDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/email")
public class SendEmailController {
	private KafkaTemplate<String, String> kafkaTemplate;
	private ObjectMapper objectMapper;

	public SendEmailController(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
		this.kafkaTemplate = kafkaTemplate;
		this.objectMapper = objectMapper;
	}

	@GetMapping("/{emailId}")
	public SendEmailEventDto sendPaymentSuccessEmail(@PathVariable("emailId") String emailId) {
		SendEmailEventDto sendEmailEventDto = new SendEmailEventDto();
		sendEmailEventDto.setTo(emailId);
		sendEmailEventDto.setSubject("Your order has been successfull");
		sendEmailEventDto.setBody("Your ooder has been successfully placed");
		sendEmailEventDto.setFrom("kratiy.5@gmail.com");
		try {
			kafkaTemplate.send("sendEmail", objectMapper.writeValueAsString(sendEmailEventDto));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sendEmailEventDto;
	}

}
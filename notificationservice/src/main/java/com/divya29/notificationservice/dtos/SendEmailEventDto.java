package com.divya29.notificationservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendEmailEventDto {
	private String to;
	private String from;
	private String subject;
	private String body;
}
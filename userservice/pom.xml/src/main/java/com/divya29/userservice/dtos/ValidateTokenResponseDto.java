package com.divya29.userservice.dtos;

import com.divya29.userservice.models.SessionStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateTokenResponseDto {
	SessionStatus sessionStatus;
	UserResponseDto userResponseDto;
}

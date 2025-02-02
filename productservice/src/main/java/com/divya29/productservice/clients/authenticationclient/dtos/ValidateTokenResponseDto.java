package com.divya29.productservice.clients.authenticationclient.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateTokenResponseDto {
    SessionStatus sessionStatus;
    UserResponseDto userResponseDto;
}

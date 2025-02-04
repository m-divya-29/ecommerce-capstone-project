package com.divya29.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutRequestDto {
        private Long userId;
        private String token;
    }



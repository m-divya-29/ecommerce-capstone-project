package com.divya29.productservice.clients.authenticationclient.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserResponseDto {
    private String email;
    private String firstName;
    private String lastName;
    private Set<Role> roles = new HashSet<>();

}

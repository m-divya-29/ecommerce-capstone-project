package com.divya29.userservice.dtos;

import java.util.Set;

import com.divya29.userservice.models.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private Long id;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private Set<Role> roles;
	private boolean isEmailVerified;
}

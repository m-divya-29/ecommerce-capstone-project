package com.divya29.userservice.dtos;

import com.divya29.userservice.models.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
	private String email;
	private String password;
	private String firstName;
	private String lastName;

	public User toUser() {
		User user = new User();
		user.setEmail(this.email);
		user.setPassword(this.password);
		user.setFirstName(this.firstName);
		user.setLastName(this.lastName);

		return user;
	}
}

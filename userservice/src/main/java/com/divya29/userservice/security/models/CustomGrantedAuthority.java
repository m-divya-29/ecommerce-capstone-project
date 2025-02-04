package com.divya29.userservice.security.models;

import org.springframework.security.core.GrantedAuthority;

import com.divya29.userservice.models.Role;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.NoArgsConstructor;

@JsonDeserialize
@NoArgsConstructor
public class CustomGrantedAuthority implements GrantedAuthority {
	private String authority;

	public CustomGrantedAuthority(Role role) {
		this.authority = role.getRole();
	}

	@Override
	public String getAuthority() {
		return this.authority;
	}
}

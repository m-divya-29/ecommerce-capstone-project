package com.divya29.userservice.dtos;

import com.divya29.userservice.models.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoleResponseDto {
	private Long id;
	private String role;

	public static RoleResponseDto fromRole(Role role) {
		return new RoleResponseDto(role.getId(), role.getRole());
	}
}

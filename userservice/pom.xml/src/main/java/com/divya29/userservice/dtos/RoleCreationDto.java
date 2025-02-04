package com.divya29.userservice.dtos;

import com.divya29.userservice.models.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleCreationDto {
	private String roleName;

	public Role toRole() {
		Role role = new Role();
		role.setRole(this.roleName);
		return role;
	}
}

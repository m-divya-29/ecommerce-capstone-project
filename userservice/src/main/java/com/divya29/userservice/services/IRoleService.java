package com.divya29.userservice.services;

import java.util.List;

import com.divya29.userservice.exceptions.NotFoundException;
import com.divya29.userservice.models.Role;

public interface IRoleService {
	Role createRole(Role role);

	Role updateRole(Long id, Role roleDetails) throws NotFoundException;

	void deleteRole(Long id) throws NotFoundException;

	Role getRoleById(Long id) throws NotFoundException;

	List<Role> getAllRoles();
}

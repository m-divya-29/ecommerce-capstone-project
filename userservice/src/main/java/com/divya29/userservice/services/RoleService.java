package com.divya29.userservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.divya29.userservice.exceptions.NotFoundException;
import com.divya29.userservice.models.Role;
import com.divya29.userservice.repositories.RoleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleService implements IRoleService {

	private RoleRepository roleRepository;

	@Override
	public Role createRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public Role updateRole(Long id, Role roleDetails) throws NotFoundException {
		Optional<Role> optionalRole = roleRepository.findById(id);
		if (optionalRole.isPresent()) {
			Role role = optionalRole.get();
			role.setRole(roleDetails.getRole());
			return roleRepository.save(role);
		} else {
			throw new NotFoundException("Role not found with id " + id);
		}
	}

	@Override
	public void deleteRole(Long id) throws NotFoundException {
		if (roleRepository.existsById(id)) {
			roleRepository.deleteById(id);
		} else {
			throw new NotFoundException("Role not found with id " + id);
		}
	}

	@Override
	public Role getRoleById(Long id) throws NotFoundException {
		return roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Role not found with id " + id));
	}

	@Override
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}
}

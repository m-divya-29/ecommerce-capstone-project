package com.divya29.userservice.services;

import java.util.List;

import com.divya29.userservice.dtos.UserDTO;
import com.divya29.userservice.exceptions.NotFoundException;
import com.divya29.userservice.models.User;

public interface IUserService {
	User getUser(Long id) throws NotFoundException;

	User updateUser(Long id, UserDTO userDTO) throws NotFoundException;

	void deleteUser(Long id) throws NotFoundException;

	List<User> listUsers();
}

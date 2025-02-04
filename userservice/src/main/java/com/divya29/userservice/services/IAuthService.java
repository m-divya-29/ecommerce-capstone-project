package com.divya29.userservice.services;

import java.util.Set;

import org.springframework.http.ResponseEntity;

import com.divya29.userservice.dtos.UserResponseDto;
import com.divya29.userservice.dtos.ValidateTokenResponseDto;
import com.divya29.userservice.exceptions.InvalidCredentialsException;
import com.divya29.userservice.exceptions.NotFoundException;
import com.divya29.userservice.exceptions.UserAlreadyExistsException;
import com.divya29.userservice.models.User;

public interface IAuthService {
	User signUp(String email, String password, String firstName, String lastName)
			throws NotFoundException, UserAlreadyExistsException;

	ResponseEntity<UserResponseDto> login(String email, String password)
			throws NotFoundException, InvalidCredentialsException;

	void logout(String token, Long UserId);

	ValidateTokenResponseDto validateToken(String token, Long userId) throws NotFoundException;

	User assignRolesToUser(Long userId, Set<Long> roleIds);
}

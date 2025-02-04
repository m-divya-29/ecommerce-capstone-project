package com.divya29.userservice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divya29.userservice.dtos.LoginRequestDto;
import com.divya29.userservice.dtos.LogoutRequestDto;
import com.divya29.userservice.dtos.RoleIdsRequestDto;
import com.divya29.userservice.dtos.SignupRequestDto;
import com.divya29.userservice.dtos.UserResponseDto;
import com.divya29.userservice.dtos.ValidateTokenRequestDto;
import com.divya29.userservice.dtos.ValidateTokenResponseDto;
import com.divya29.userservice.exceptions.InvalidCredentialsException;
import com.divya29.userservice.exceptions.NotFoundException;
import com.divya29.userservice.exceptions.UserAlreadyExistsException;
import com.divya29.userservice.models.User;
import com.divya29.userservice.services.IAuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private IAuthService authService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody SignupRequestDto signupRequestDto) throws UserAlreadyExistsException, NotFoundException {

        User createdUser = authService.signUp(signupRequestDto.getEmail(), signupRequestDto.getPassword(), signupRequestDto.getFirstName(), signupRequestDto.getLastName());

        logger.info("User created {}", createdUser );
        return new ResponseEntity<>(UserResponseDto.fromUser(createdUser),
                HttpStatus.OK);
    }

        @PostMapping("/login")
        public ResponseEntity<UserResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) throws InvalidCredentialsException, NotFoundException {
            return authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        }

        @PostMapping("/validate")
        public ResponseEntity<ValidateTokenResponseDto> validateToken(@RequestBody ValidateTokenRequestDto validateTokenRequestDto) throws NotFoundException {
            ValidateTokenResponseDto validateTokenResponseDto = authService.validateToken(validateTokenRequestDto.getToken(), validateTokenRequestDto.getUserId());
            return new ResponseEntity<ValidateTokenResponseDto>(validateTokenResponseDto, HttpStatus.OK);
        }

        @PostMapping("/logout")
        public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto){
            authService.logout(logoutRequestDto.getToken(), logoutRequestDto.getUserId());
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{userId}/roles")
    public ResponseEntity<UserResponseDto> assignRolesToUser(@PathVariable Long userId, @RequestBody RoleIdsRequestDto requestDto) {
        User user = authService.assignRolesToUser(userId, requestDto.getRoleIds());
        return ResponseEntity.ok(UserResponseDto.fromUser(user));
    }
}

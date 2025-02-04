package com.divya29.userservice.security.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.divya29.userservice.models.User;
import com.divya29.userservice.repositories.UserRepository;
import com.divya29.userservice.security.models.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	private UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByEmail(email);

		if (user.isEmpty()) {
			throw new UsernameNotFoundException(email + " doesn't exist.");
		}

		return new CustomUserDetails(user.get());
	}
}
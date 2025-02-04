package com.divya29.userservice.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseModel {
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles = new HashSet<>();
	private boolean isEmailVerified;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Address> addresses;
}

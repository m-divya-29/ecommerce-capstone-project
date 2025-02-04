package com.divya29.userservice.models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Session extends BaseModel {
	private String token;
	private Date expiringAt;

	@ManyToOne
	private User user;
	@Enumerated(EnumType.ORDINAL)
	private SessionStatus sessionStatus;
}

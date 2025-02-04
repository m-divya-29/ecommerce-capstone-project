package com.divya29.userservice.dtos;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleIdsRequestDto {
	private Set<Long> roleIds;
}

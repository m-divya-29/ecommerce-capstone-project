package com.divya29.orderservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO {
	private Long id;
	private Long productId;
	private Integer quantity;
}

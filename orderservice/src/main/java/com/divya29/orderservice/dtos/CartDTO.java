package com.divya29.orderservice.dtos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO {
	private Long id;
	private Long userId;
	private List<CartItemDTO> cartItems;

}

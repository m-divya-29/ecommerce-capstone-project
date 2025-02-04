package com.divya29.cartservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO {
	private Long id;
	private Long productId;
	private Integer quantity;

}

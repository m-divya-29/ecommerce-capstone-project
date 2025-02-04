package com.divya29.cartservice.dtos;

import java.util.List;
import java.util.stream.Collectors;

import com.divya29.cartservice.models.Cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO {
	private Long id;
	private Long userId;
	private List<CartItemDTO> cartItems;

	public static CartDTO fromCartEntity(Cart cart) {
		CartDTO cartDTO = new CartDTO();
		cartDTO.setUserId(cart.getUserId());
		cartDTO.setId(cart.getId());

		List<CartItemDTO> cartItemDTOs = cart.getCartItems().stream().map(cartItem -> {
			CartItemDTO cartItemDTO = new CartItemDTO();
			cartItemDTO.setId(cartItem.getId());
			cartItemDTO.setProductId(cartItem.getProductId());
			cartItemDTO.setQuantity(cartItem.getQuantity());
			return cartItemDTO;
		}).collect(Collectors.toList());

		cartDTO.setCartItems(cartItemDTOs);
		return cartDTO;
	}
}

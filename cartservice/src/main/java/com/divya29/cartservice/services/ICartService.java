package com.divya29.cartservice.services;

import java.util.Optional;

import com.divya29.cartservice.models.Cart;

import jakarta.transaction.Transactional;

public interface ICartService {
	Optional<Cart> getCartByUserId(Long userId);

	@Transactional
	Cart addToCart(Long userId, Long productId, Integer quantity);

	boolean softDeleteCart(Long userId);
}

package com.divya29.cartservice.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.divya29.cartservice.exceptions.CartNotFoundException;
import com.divya29.cartservice.models.Cart;
import com.divya29.cartservice.models.CartItem;
import com.divya29.cartservice.repositories.CartItemRepository;
import com.divya29.cartservice.repositories.CartRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService implements ICartService {

	private final CartRepository cartRepository;

	public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository) {
		this.cartRepository = cartRepository;
	}

	@Override
	public Optional<Cart> getCartByUserId(Long userId) {
		return cartRepository.findByUserIdAndIsDeletedFalse(userId);

	}

	@Override
	@Transactional
	public Cart addToCart(Long userId, Long productId, Integer quantity) {
		// Find the cart by userId
		Optional<Cart> cartOptional = getCartByUserId(userId);

		Cart cart;
		if (cartOptional.isPresent()) {
			cart = cartOptional.get();
		} else {
			// If cart does not exist, create a new one
			cart = new Cart();
			cart.setUserId(userId);
			cart.setCartItems(new ArrayList<>());
			cart = cartRepository.save(cart);
		}

		// Check if the product is already in the cart
		Optional<CartItem> existingCartItem = cart.getCartItems().stream()
				.filter(item -> item.getProductId().equals(productId)).findFirst();

		if (existingCartItem.isPresent()) {
			// If product is already in the cart, update the quantity
			CartItem cartItem = existingCartItem.get();
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
		} else {
			// If product is not in the cart, add it as a new CartItem
			CartItem newCartItem = new CartItem();
			newCartItem.setProductId(productId);
			newCartItem.setQuantity(quantity);
			newCartItem.setCart(cart);

			cart.getCartItems().add(newCartItem);
		}

		return cartRepository.save(cart);
	}

	@Override
	public boolean softDeleteCart(Long userId) {
		Optional<Cart> cartOptional = getCartByUserId(userId);
		if (cartOptional.isPresent()) {
			Cart cart = cartOptional.get();
			cart.setDeleted(true);
			cartRepository.save(cart);
			return true;
		} else {
			throw new CartNotFoundException("Cart not found for userId: " + userId);
		}
	}

}

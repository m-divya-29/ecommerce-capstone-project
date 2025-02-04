package com.divya29.cartservice.controllers;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divya29.cartservice.dtos.AddToCartRequestDTO;
import com.divya29.cartservice.dtos.CartDTO;
import com.divya29.cartservice.exceptions.CartNotFoundException;
import com.divya29.cartservice.models.Cart;
import com.divya29.cartservice.services.CartService;
import com.divya29.cartservice.services.JwtUtils;

@RestController
@RequestMapping("/cart")
public class CartController {
	private final CartService cartService;

	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	@GetMapping("/user")
	public ResponseEntity<CartDTO> getCartByUserId(Authentication authentication) {

		Long userId = JwtUtils.extractUserIdClaim(authentication);

		Optional<Cart> cartOptional = cartService.getCartByUserId(userId);
		if (cartOptional.isPresent()) {
			return ResponseEntity.ok(CartDTO.fromCartEntity(cartOptional.get()));
		} else {
			throw new CartNotFoundException("Cart not found for userId: " + userId);
		}

	}

	@PostMapping("")
	public ResponseEntity<CartDTO> addToCart(@RequestBody AddToCartRequestDTO request, Authentication authentication) {
		Long userId = JwtUtils.extractUserIdClaim(authentication);
		Cart updatedCart = cartService.addToCart(userId, request.getProductId(), request.getQuantity());
		return ResponseEntity.ok(CartDTO.fromCartEntity(updatedCart));
	}

	@DeleteMapping("")
	public ResponseEntity<Boolean> deleteCart(Authentication authentication) {
		Long userId = JwtUtils.extractUserIdClaim(authentication);
		return ResponseEntity.ok(cartService.softDeleteCart(userId));
	}

}

package com.divya29.orderservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;

import com.divya29.orderservice.dtos.CartDTO;
import com.divya29.orderservice.exceptions.AuthenticationException;
import com.divya29.orderservice.exceptions.InsufficientQuantity;
import com.divya29.orderservice.exceptions.NotFoundException;
import com.divya29.orderservice.models.Order;
import com.divya29.orderservice.models.OrderStatus;

public interface IOrderService {
	default void validateCart(CartDTO cart, Long userId) throws NotFoundException {

		if (!cart.getUserId().equals(userId)) {
			throw new AuthenticationException("You are not authorized to checkout this cart.");
		}

		if (cart.getCartItems().isEmpty()) {
			throw new NotFoundException("No items found in the cart with id " + cart.getId());
		}

	}

	Order processCheckout(Authentication authentication) throws NotFoundException, InsufficientQuantity;

	Order createOrder(Order order) throws InsufficientQuantity;

	Order addAddressToOrder(Long orderId, Long addressId) throws NotFoundException;

	Optional<Order> getOrderById(Long id);

	Order updateOrderStatus(Long id, OrderStatus status) throws NotFoundException;

	boolean deleteOrder(Long id);

	List<Order> listOrders();

	List<Order> getOrdersByCustomerId(Long customerId);

	Order updateOrderWithPaymentId(Long orderId, Long paymentId) throws NotFoundException;

	Order processOrderConfirmation(Long orderId) throws NotFoundException;
}

package com.divya29.orderservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.divya29.orderservice.clients.CartServiceClient;
import com.divya29.orderservice.clients.ProductServiceClient;
import com.divya29.orderservice.dtos.CartDTO;
import com.divya29.orderservice.dtos.CartItemDTO;
import com.divya29.orderservice.dtos.Product;
import com.divya29.orderservice.exceptions.InsufficientQuantity;
import com.divya29.orderservice.exceptions.NotFoundException;
import com.divya29.orderservice.models.Order;
import com.divya29.orderservice.models.OrderItem;
import com.divya29.orderservice.models.OrderStatus;
import com.divya29.orderservice.repositories.OrderItemRepository;
import com.divya29.orderservice.repositories.OrderRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class OrderService implements IOrderService {

	private OrderRepository orderRepository;

	private OrderItemRepository orderItemRepository;

	private final ProductServiceClient productServiceClient;
	private final CartServiceClient cartServiceClient;

	@Override
	public Order processCheckout(Authentication authentication) throws NotFoundException, InsufficientQuantity {
		Long userId = JwtUtils.extractUserIdClaim(authentication);
		String token = JwtUtils.extractTokenValue(authentication);

		CartDTO cart = cartServiceClient.getCart(userId, token);

		// Validate Cart for user_id
		validateCart(cart, userId);

		Order order = new Order();
		order.setCustomerId(userId);
		order.setOrderStatus(OrderStatus.PENDING);

		Double totalAmount = (double) 0;
		// Check product stock
		for (CartItemDTO item : cart.getCartItems()) {
			Product product = productServiceClient.getProductById(item.getProductId());

			if (product == null || product.getStockQuantity() < item.getQuantity()) {
				throw new InsufficientQuantity("Insufficient stock for product with ID " + item.getProductId());
			}
			totalAmount = totalAmount + item.getQuantity() * product.getPrice();

			OrderItem orderItem = new OrderItem();
			orderItem.setProductId(item.getProductId());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setPrice(product.getPrice());
			orderItem.setOrder(order);
			order.getItems().add(orderItem);

			// Update the stock quantity of the product
			product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
			productServiceClient.updateProduct(product); // Update the product stock in the Product Service
		}

		order.setTotalAmount(totalAmount);
		// Proceed with order creation
		Order savedOrder = orderRepository.save(order);

		cartServiceClient.deleteCart(token);

		return savedOrder;

	}

	@Override
	public Order createOrder(Order order) throws InsufficientQuantity {
		Double totalAmount = (double) 0;
		// Check product stock
		for (OrderItem item : order.getItems()) {
			Product product = productServiceClient.getProductById(item.getProductId());

			if (product == null || product.getStockQuantity() < item.getQuantity()) {
				throw new InsufficientQuantity("Insufficient stock for product with ID " + item.getProductId());
			}
			totalAmount = totalAmount + item.getQuantity() * item.getPrice();
		}
		order.setTotalAmount(totalAmount);
		// Proceed with order creation
		Order savedOrder = orderRepository.save(order);
		// Save order items
		for (OrderItem item : order.getItems()) {
			item.setOrder(savedOrder);
			orderItemRepository.save(item);

			// Update the stock quantity of the product
			Product product = productServiceClient.getProductById(item.getProductId());
			product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
			productServiceClient.updateProduct(product); // Update the product stock in the Product Service
		}

		// Proceed with payment processing and email notification
		// ...

		return savedOrder;
	}

	@Override
	public Order addAddressToOrder(Long orderId, Long addressId) throws NotFoundException {
		Order order = getOrderById(orderId)
				.orElseThrow(() -> new NotFoundException("Order not found with id " + orderId));

		// Update order with address ID
		order.setAddressId(addressId);
		return orderRepository.save(order);

	}

	@Override
	public Optional<Order> getOrderById(Long id) {
		return orderRepository.findById(id);
	}

	@Override
	public Order updateOrderStatus(Long id, OrderStatus status) throws NotFoundException {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Order not found with id: " + id));
		order.setOrderStatus(status);
		return orderRepository.save(order);
	}

	@Override
	public boolean deleteOrder(Long id) {
		if (orderRepository.existsById(id)) {
			orderRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public List<Order> listOrders() {
		return orderRepository.findAll();
	}

	@Override
	public List<Order> getOrdersByCustomerId(Long customerId) {
		return orderRepository.findAllByCustomerId(customerId);
	}

	@Override
	public Order updateOrderWithPaymentId(Long orderId, Long paymentId) throws NotFoundException {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new NotFoundException("Order not found with id: " + orderId));
		order.setPaymentId(paymentId);
		return orderRepository.save(order);
	}

	@Override
	public Order processOrderConfirmation(Long orderId) throws NotFoundException {
		Optional<Order> orderOptional = getOrderById(orderId);
		if (orderOptional.isEmpty()) {
			throw new NotFoundException("Order not found with id: " + orderId);
		}

		Order order = orderOptional.get();
		return order;
	}
}
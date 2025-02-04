package com.divya29.orderservice.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divya29.orderservice.dtos.OrderAddressRequestDTO;
import com.divya29.orderservice.dtos.OrderDTO;
import com.divya29.orderservice.dtos.OrderStatusDTO;
import com.divya29.orderservice.exceptions.InsufficientQuantity;
import com.divya29.orderservice.exceptions.NotFoundException;
import com.divya29.orderservice.models.Order;
import com.divya29.orderservice.models.OrderStatus;
import com.divya29.orderservice.services.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping("/checkout")
	public ResponseEntity<OrderDTO> checkout(Authentication authentication)
			throws InsufficientQuantity, NotFoundException {

		Order savedOrder = orderService.processCheckout(authentication);

		return new ResponseEntity<>(OrderDTO.toOrderDTO(savedOrder), HttpStatus.CREATED);

	}

	@GetMapping("/{id}")
	public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
		return orderService.getOrderById(id)
				.map(order -> new ResponseEntity<>(OrderDTO.toOrderDTO(order), HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PutMapping("/{id}/status")
	public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long id, @RequestBody OrderStatusDTO statusDTO)
			throws NotFoundException {
		Order updatedOrder = orderService.updateOrderStatus(id, OrderStatus.valueOf(statusDTO.getStatus()));
		return new ResponseEntity<>(OrderDTO.toOrderDTO(updatedOrder), HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
		if (orderService.deleteOrder(id)) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping
	public ResponseEntity<List<OrderDTO>> listOrders() {
		List<Order> orders = orderService.listOrders();
		List<OrderDTO> orderDTO = orders.stream().map(OrderDTO::toOrderDTO).collect(Collectors.toList());
		return new ResponseEntity<>(orderDTO, HttpStatus.OK);
	}

	@GetMapping("/customers/{customerId}")
	public ResponseEntity<List<OrderDTO>> getOrdersByCustomerId(@PathVariable Long customerId) {
		List<Order> orders = orderService.getOrdersByCustomerId(customerId);
		List<OrderDTO> orderDTO = orders.stream().map(OrderDTO::toOrderDTO).collect(Collectors.toList());
		return new ResponseEntity<>(orderDTO, HttpStatus.OK);
	}

	@GetMapping("/confirmation")
	public String orderConfirmation(@RequestParam Long orderId) throws NotFoundException {
		orderService.processOrderConfirmation(orderId);
		return "Your order confirmation with id " + orderId.toString() + " is in process.";
	}

	@PutMapping("/{orderId}/payment/{paymentId}")
	public ResponseEntity<OrderDTO> updateOrderWithPaymentId(@PathVariable("orderId") Long orderId,
			@PathVariable("paymentId") Long paymentId) throws NotFoundException {
		Order updatedOrder = orderService.updateOrderWithPaymentId(orderId, paymentId);
		return new ResponseEntity<>(OrderDTO.toOrderDTO(updatedOrder), HttpStatus.OK);
	}

	@PostMapping("/{orderId}/address")
	public ResponseEntity<String> addAddressToOrder(@PathVariable("orderId") Long orderId,
			@RequestBody OrderAddressRequestDTO requestDTO) throws NotFoundException {
		orderService.addAddressToOrder(orderId, requestDTO.getAddressId());
		return ResponseEntity.ok("Address added to order successfully");
	}
}

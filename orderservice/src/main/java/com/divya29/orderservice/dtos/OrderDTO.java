package com.divya29.orderservice.dtos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.divya29.orderservice.models.Order;
import com.divya29.orderservice.models.OrderItem;
import com.divya29.orderservice.models.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
	private Long id;
	private Long customerId;
	private List<OrderItemDTO> items;
	private String status;
	private Double totalAmount;
	private Long paymentId;
	private Long addressId;

	public Order toOrder() {
		Order order = new Order();
		order.setCustomerId(this.getCustomerId());
		order.setTotalAmount(this.getTotalAmount());
		order.setPaymentId(getPaymentId());
		order.setAddressId(this.getAddressId());

		try {
			OrderStatus status = OrderStatus.valueOf(this.getStatus());
			order.setOrderStatus(status);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid order status");
		}

		List<OrderItem> orderItems = this.getItems().stream().map(itemDTO -> {
			OrderItem item = new OrderItem();
			item.setProductId(itemDTO.getProductId());
			item.setQuantity(itemDTO.getQuantity());
			item.setPrice(itemDTO.getPrice());
			return item;
		}).collect(Collectors.toList());

		order.setItems(orderItems);
		return order;
	}

	public static OrderDTO toOrderDTO(Order order) {
		if (order == null) {
			return null;
		}

		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(order.getId());
		orderDTO.setCustomerId(order.getCustomerId());
		orderDTO.setStatus(String.valueOf(order.getOrderStatus()));
		orderDTO.setTotalAmount(order.getTotalAmount());
		orderDTO.setPaymentId(order.getPaymentId());
		orderDTO.setAddressId(order.getAddressId());

		List<OrderItemDTO> itemDTOs = order.getItems().stream().map(OrderItemDTO::toOrderItemDTO)
				.collect(Collectors.toList());
		orderDTO.setItems(itemDTOs);

		return orderDTO;
	}
}
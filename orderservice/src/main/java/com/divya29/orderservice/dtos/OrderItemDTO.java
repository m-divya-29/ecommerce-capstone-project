package com.divya29.orderservice.dtos;

import com.divya29.orderservice.models.OrderItem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {
	private Long id;
	private Long productId;
	private int quantity;
	private Double price;

	public static OrderItemDTO toOrderItemDTO(OrderItem orderItem) {
		if (orderItem == null) {
			return null;
		}

		OrderItemDTO orderItemDTO = new OrderItemDTO();
		orderItemDTO.setId(orderItem.getId());
		orderItemDTO.setProductId(orderItem.getProductId());
		orderItemDTO.setQuantity(orderItem.getQuantity());
		orderItemDTO.setPrice(orderItem.getPrice());

		return orderItemDTO;
	}
}
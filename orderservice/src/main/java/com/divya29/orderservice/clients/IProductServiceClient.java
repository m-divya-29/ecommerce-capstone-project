package com.divya29.orderservice.clients;

import com.divya29.orderservice.dtos.Product;

public interface IProductServiceClient {
	Product getProductById(Long productId);

	Product updateProduct(Product product);
}

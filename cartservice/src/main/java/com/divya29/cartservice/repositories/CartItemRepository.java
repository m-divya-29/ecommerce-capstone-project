package com.divya29.cartservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.divya29.cartservice.models.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}

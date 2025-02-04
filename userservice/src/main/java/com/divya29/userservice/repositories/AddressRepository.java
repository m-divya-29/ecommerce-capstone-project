package com.divya29.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.divya29.userservice.models.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}

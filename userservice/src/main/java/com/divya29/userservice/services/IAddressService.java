package com.divya29.userservice.services;

import java.util.List;

import com.divya29.userservice.exceptions.NotFoundException;
import com.divya29.userservice.models.Address;

public interface IAddressService {
	Address addAddressToUser(Long userId, Address address) throws NotFoundException;

	// Method to find all addresses for a user
	List<Address> findAllAddressesByUserId(Long userId) throws NotFoundException;

	// Method to update an existing address
	Address updateAddress(Long addressId, Address newAddressDetails) throws NotFoundException;
}

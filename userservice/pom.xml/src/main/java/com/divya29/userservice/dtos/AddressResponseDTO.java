package com.divya29.userservice.dtos;

import com.divya29.userservice.models.Address;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressResponseDTO {
	private Long id;
	private String houseNo;
	private String street;
	private String city;
	private String state;
	private String postalCode;
	private String country;
	private String phoneNumber;

	public static AddressResponseDTO fromEntity(Address address) {
		AddressResponseDTO dto = new AddressResponseDTO();
		dto.setId(address.getId());
		dto.setHouseNo(address.getHouseNo());
		dto.setStreet(address.getStreet());
		dto.setCity(address.getCity());
		dto.setState(address.getState());
		dto.setPostalCode(address.getPostalCode());
		dto.setCountry(address.getCountry());
		dto.setPhoneNumber(address.getPhoneNumber());
		return dto;
	}
}

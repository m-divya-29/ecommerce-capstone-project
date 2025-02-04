package com.divya29.userservice.dtos;

import com.divya29.userservice.models.Address;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequestDTO {
	private String houseNo;
	private String street;
	private String city;
	private String state;
	private String postalCode;
	private String country;
	private String phoneNumber;

	public Address toEntity() {
		Address address = new Address();
		address.setHouseNo(this.getHouseNo());
		address.setStreet(this.getStreet());
		address.setCity(this.getCity());
		address.setState(this.getState());
		address.setPostalCode(this.getPostalCode());
		address.setCountry(this.getCountry());
		address.setPhoneNumber(this.getPhoneNumber());
		return address;
	}
}

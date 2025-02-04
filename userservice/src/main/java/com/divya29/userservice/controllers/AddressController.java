package com.divya29.userservice.controllers;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divya29.userservice.dtos.AddressRequestDTO;
import com.divya29.userservice.dtos.AddressResponseDTO;
import com.divya29.userservice.exceptions.NotFoundException;
import com.divya29.userservice.models.Address;
import com.divya29.userservice.services.IAddressService;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final IAddressService addressService;


    public AddressController(IAddressService addressService) {
        this.addressService = addressService;
    }


    @PostMapping("/user/{userId}")
    public ResponseEntity<AddressResponseDTO> addAddressToUser(@PathVariable Long userId, @RequestBody AddressRequestDTO addressRequest) throws NotFoundException {
        Address addedAddress = addressService.addAddressToUser(userId, addressRequest.toEntity());
        return ResponseEntity.ok(AddressResponseDTO.fromEntity(addedAddress));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressResponseDTO>> getAllAddressesByUserId(@PathVariable Long userId) throws NotFoundException {
        List<Address> addresses = addressService.findAllAddressesByUserId(userId);
        List<AddressResponseDTO> addressDTOs = addresses.stream().map(AddressResponseDTO::fromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(addressDTOs);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable Long addressId, @RequestBody AddressRequestDTO addressRequest) throws NotFoundException {
        Address updatedAddress = addressService.updateAddress(addressId, addressRequest.toEntity());
        return ResponseEntity.ok(AddressResponseDTO.fromEntity(updatedAddress));
    }
}

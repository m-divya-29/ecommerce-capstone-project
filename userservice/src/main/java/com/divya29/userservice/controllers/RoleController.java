package com.divya29.userservice.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divya29.userservice.dtos.RoleCreationDto;
import com.divya29.userservice.dtos.RoleResponseDto;
import com.divya29.userservice.exceptions.NotFoundException;
import com.divya29.userservice.models.Role;
import com.divya29.userservice.services.IRoleService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/roles")
public class RoleController {

    private IRoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleResponseDto>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        List<RoleResponseDto> roleResponseDtos = roles.stream()
                .map(RoleResponseDto::fromRole)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roleResponseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDto> getRoleById(@PathVariable Long id) throws NotFoundException {
        Role role = roleService.getRoleById(id);
        return ResponseEntity.ok(RoleResponseDto.fromRole(role));
    }

    @PostMapping
    public ResponseEntity<RoleResponseDto> createRole(@RequestBody RoleCreationDto roleCreationDto) {
        Role createdRole = roleService.createRole(roleCreationDto.toRole());
        return new ResponseEntity<>(RoleResponseDto.fromRole(createdRole), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDto> updateRole(@PathVariable Long id, @RequestBody RoleCreationDto roleCreationDto) throws NotFoundException {
        Role updatedRole = roleService.updateRole(id, roleCreationDto.toRole());
        return ResponseEntity.ok(RoleResponseDto.fromRole(updatedRole));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) throws NotFoundException {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }


}

package com.example.springboot.address.dtos;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record AddressResponseDTO(

        Long id,
        String neighborhood,
        String street,
        String number,
        String complement,
        String city,
        String state,
        String zipCode,
        String country,
        Long userId,
        LocalDateTime createdAt

) {}

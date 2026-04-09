package com.example.springboot.address.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressUpdateDTO(

        @NotBlank
        String neighborhood,

        @NotBlank
        String street,

        @NotBlank
        String number,

        @NotBlank
        String complement,

        @NotBlank
        String city,

        @NotBlank
        String state,

        @NotBlank
        @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos")
        String zipCode,

        @NotBlank
        String country

) {}

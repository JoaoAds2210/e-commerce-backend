package com.example.springboot.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserCreateDTO(
        @NotBlank
        @Size(min = 3, max = 100)
        String name,

        @NotBlank
        String cpf,

        @Email
        @NotBlank
        String email,

        @NotBlank
        String phone,

        @NotBlank
        @Size(min = 8)
        String password,

        @NotNull
        LocalDate birthDate
) {}

package com.example.springboot.user.dtos;

import jakarta.validation.constraints.*;

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
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).+$",
                message = "A senha deve conter ao menos uma letra maiúscula, um número e um caractere especial"
        )

        @NotNull
        LocalDate birthDate
) {}

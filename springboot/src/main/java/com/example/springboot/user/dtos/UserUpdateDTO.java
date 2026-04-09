package com.example.springboot.user.dtos;

import jakarta.validation.constraints.Email;

import java.time.LocalDate;

public record UserUpdateDTO(
        String name,

        @Email
        String email,

        String phone,

        String password,

        LocalDate birthDate,

        Boolean active
) {}

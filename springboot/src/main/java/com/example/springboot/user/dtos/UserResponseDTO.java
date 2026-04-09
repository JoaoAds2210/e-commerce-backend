package com.example.springboot.user.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserResponseDTO(
        Long idUser,

        String name,

        String email,

        String cpf,

        String phone,

        LocalDate birthDate,

        Boolean active,

        LocalDateTime createdAt
) {}

package com.example.springboot.user.mapper;

import com.example.springboot.user.dtos.UserCreateDTO;
import com.example.springboot.user.dtos.UserResponseDTO;
import com.example.springboot.user.entity.User;

import java.time.LocalDateTime;

public class UserMapper {

    public static User toEntity(UserCreateDTO dto){
        return User.builder()
                .name(dto.name())
                .cpf(dto.cpf())
                .email(dto.email())
                .phone(dto.phone())
                .birthDate(dto.birthDate())
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static UserResponseDTO toDTO(User user) {

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCpf(),
                user.getPhone(),
                user.getBirthDate(),
                user.getActive(),
                user.getCreatedAt()
        );
    }
}

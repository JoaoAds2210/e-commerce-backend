package com.example.springboot.user.service;

import com.example.springboot.user.dtos.UserCreateDTO;
import com.example.springboot.user.dtos.UserResponseDTO;
import com.example.springboot.user.dtos.UserUpdateDTO;


import java.util.List;


public interface UserServices {

    List<UserResponseDTO> findUsers();

    UserResponseDTO findPorId(Long id);

    UserResponseDTO findPorEmail(String email);

    UserResponseDTO create(UserCreateDTO dto);

    UserResponseDTO update(Long id, UserUpdateDTO dto);

    void delete(Long id);
}

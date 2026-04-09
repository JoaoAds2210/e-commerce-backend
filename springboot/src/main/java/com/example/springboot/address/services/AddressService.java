package com.example.springboot.address.services;

import com.example.springboot.address.dtos.AddressCreateDTO;
import com.example.springboot.address.dtos.AddressResponseDTO;
import com.example.springboot.address.dtos.AddressUpdateDTO;

import java.util.List;

public interface AddressService {

    List<AddressResponseDTO> findAll();
    AddressResponseDTO findById(Long id);
    List<AddressResponseDTO> findByUserId(Long userId);
    AddressResponseDTO create(AddressCreateDTO dto);
    AddressResponseDTO update(Long id, AddressUpdateDTO dto);
    void delete(Long id);
}

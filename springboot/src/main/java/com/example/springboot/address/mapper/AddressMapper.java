package com.example.springboot.address.mapper;

import com.example.springboot.address.dtos.AddressCreateDTO;
import com.example.springboot.address.dtos.AddressResponseDTO;
import com.example.springboot.address.entity.Address;
import com.example.springboot.user.entity.User;

public class AddressMapper {

    public static Address toEntity(AddressCreateDTO dto, User user){
        return Address.builder()
                .neighborhood(dto.neighborhood())
                .street(dto.street())
                .number(dto.number())
                .complement(dto.complement())
                .city(dto.city())
                .state(dto.state())
                .zipCode(dto.zipCode())
                .country(dto.country())
                .user(user)
                .build();
    }

    public static AddressResponseDTO toDTO(Address address){
        return new AddressResponseDTO(
                address.getId(),
                address.getNeighborhood(),
                address.getStreet(),
                address.getNumber(),
                address.getComplement(),
                address.getCity(),
                address.getState(),
                address.getZipCode(),
                address.getCountry(),
                address.getUser().getId(),
                address.getCreatedAt()
        );
    }
}

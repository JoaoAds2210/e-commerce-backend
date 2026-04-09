package com.example.springboot.address.services;

import com.example.springboot.address.dtos.AddressCreateDTO;
import com.example.springboot.address.dtos.AddressResponseDTO;
import com.example.springboot.address.dtos.AddressUpdateDTO;
import com.example.springboot.address.entity.Address;
import com.example.springboot.address.mapper.AddressMapper;
import com.example.springboot.address.repository.AddressRepository;
import com.example.springboot.user.repository.UserRepository;
import com.example.springboot.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImp implements AddressService {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<AddressResponseDTO> findAll() {
        return addressRepository.findAll()
                .stream()
                .map(AddressMapper::toDTO)
                .toList();
    }

    @Override
    public AddressResponseDTO findById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(()  -> new RuntimeException("Address not foud by ID"));

        return AddressMapper.toDTO(address);
    }

    @Override
    public List<AddressResponseDTO> findByUserId(Long userId) {
        return addressRepository.findByUserId(userId)
                .stream()
                .map(AddressMapper::toDTO)
                .toList();
    }

    @Override
    public AddressResponseDTO create(AddressCreateDTO dto) {

        User user = userRepository.findById(dto.userId())
                .orElseThrow(()-> new RuntimeException("User not found by ID"));

        Address address = AddressMapper.toEntity(dto, user);

        Address addressSaved = addressRepository.save(address);

        return AddressMapper.toDTO(addressSaved);
    }

    @Override
    public AddressResponseDTO update(Long id, AddressUpdateDTO dto) {

        Address oldAddress = addressRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Address Not Found BY id"));
        //neighborhood
        if (dto.neighborhood() != null && !dto.neighborhood().isBlank()){
            oldAddress.setNeighborhood(dto.neighborhood().trim());
        }
        //street
        if (dto.street() != null && !dto.street().isBlank()){
            oldAddress.setStreet(dto.street().trim());
        }
        //number
        if (dto.number() != null && !dto.number().isBlank()){
            oldAddress.setNumber(dto.number().trim());
        }
        //complement
        if (dto.complement() != null && !dto.complement().isBlank()){
            oldAddress.setComplement(dto.complement().trim());
        }
        //city
        if (dto.city() != null && !dto.city().isBlank()){
            oldAddress.setCity(dto.city().trim());
        }
        //state
        if (dto.state() != null && !dto.state().isBlank()){
            oldAddress.setState(dto.state().trim());
        }
        //zipcode
        if (dto.zipCode() != null && !dto.zipCode().isBlank()){
            oldAddress.setZipCode(dto.zipCode().trim());
        }
        //coutry
        if (dto.country() != null && !dto.country().isBlank()){
            oldAddress.setCountry(dto.country().trim());
        }

        Address updateAddress = addressRepository.save(oldAddress);

        return AddressMapper.toDTO(updateAddress);
    }

    @Override
    public void delete(Long id) {
        AddressResponseDTO address = findById(id);

        addressRepository.deleteById(id);
    }
}

package com.example.springboot.user.service;

import com.example.springboot.user.repository.UserRepository;
import com.example.springboot.user.dtos.UserCreateDTO;
import com.example.springboot.user.dtos.UserResponseDTO;
import com.example.springboot.user.dtos.UserUpdateDTO;
import com.example.springboot.user.entity.User;
import com.example.springboot.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServicesImp implements UserServices {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponseDTO> findUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    @Override
    public UserResponseDTO findPorId(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return UserMapper.toDTO(user);
    }

    @Override
    public UserResponseDTO findPorEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return UserMapper.toDTO(user);
    }

    @Override
    public UserResponseDTO create(UserCreateDTO dto) {

        if (userRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Email já cadastrado");
        }

        if (userRepository.existsByCpf(dto.cpf())) {
            throw new RuntimeException("CPF já cadastrado");
        }

        if (dto.birthDate().isAfter(LocalDate.now())) {
            throw new RuntimeException("Data de nascimento inválida");
        }

        User user = UserMapper.toEntity(dto);

        user.setEmail(user.getEmail().toLowerCase().trim());
        user.setPhone(user.getPhone().replaceAll("\\D", ""));
        user.setPasswordHash(passwordEncoder.encode(dto.password()));

        User savedUser = userRepository.save(user);

        return UserMapper.toDTO(savedUser);
    }

    @Override
    public UserResponseDTO update(Long id, UserUpdateDTO dto) {

        User oldUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (dto.name() != null && !dto.name().isBlank()) {
            oldUser.setName(dto.name().trim());
        }

        if (dto.birthDate() != null) {
            if (dto.birthDate().isAfter(LocalDate.now())) {
                throw new RuntimeException("Data de nascimento inválida");
            }
            oldUser.setBirthDate(dto.birthDate());
        }

        if (dto.email() != null && !dto.email().isBlank()) {

            String email = dto.email().toLowerCase().trim();

            if (!email.equals(oldUser.getEmail()) && userRepository.existsByEmail(email)) {
                throw new RuntimeException("Email já em uso");
            }

            oldUser.setEmail(email);
        }

        if (dto.phone() != null && !dto.phone().isBlank()) {
            oldUser.setPhone(dto.phone().replaceAll("\\D", ""));
        }

        if (dto.password() != null && !dto.password().isBlank()) {
            oldUser.setPasswordHash(passwordEncoder.encode(dto.password()));
        }

        if (dto.active() != null) {
            oldUser.setActive(dto.active());
        }

        User updatedUser = userRepository.save(oldUser);

        return UserMapper.toDTO(updatedUser);
    }

    @Override
    public void delete(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setActive(false);

        userRepository.save(user);
    }
}

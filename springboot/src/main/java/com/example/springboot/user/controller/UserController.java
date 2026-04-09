package com.example.springboot.user.controller;

import com.example.springboot.user.dtos.UserCreateDTO;
import com.example.springboot.user.dtos.UserResponseDTO;
import com.example.springboot.user.dtos.UserUpdateDTO;
import com.example.springboot.user.entity.User;
import com.example.springboot.user.mapper.UserMapper;
import com.example.springboot.user.service.UserServicesImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServicesImp userServices;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {

        List<UserResponseDTO> users = userServices.findUsers();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {

        UserResponseDTO user = userServices.findPorId(id);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> findByEmail(@PathVariable String email) {

        UserResponseDTO user = userServices.findPorEmail(email);

        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserCreateDTO dto) {

        UserResponseDTO user = userServices.create(dto);

        return ResponseEntity
                .status(201)
                .body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid UserUpdateDTO dto) {

        UserResponseDTO user = userServices.update(id, dto);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        userServices.delete(id);

        return ResponseEntity.noContent().build();
    }
}

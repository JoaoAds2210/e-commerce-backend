package com.example.springboot.user.controller;

import com.example.springboot.user.dtos.UserCreateDTO;
import com.example.springboot.user.dtos.UserResponseDTO;
import com.example.springboot.user.dtos.UserUpdateDTO;
import com.example.springboot.user.service.UserServicesImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuarios", description = "Gerenciamento de usuários")
public class UserController {

    @Autowired
    private UserServicesImp userServices;

    @Operation(summary = "Listar todos os usuários")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {

        List<UserResponseDTO> users = userServices.findUsers();

        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Buscar pet por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {

        UserResponseDTO user = userServices.findPorId(id);

        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Listar todos os tutores")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> findByEmail(@PathVariable String email) {

        UserResponseDTO user = userServices.findPorEmail(email);

        return ResponseEntity.ok(user);
    }

    @Operation(summary = "cadastrar usuarios")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserCreateDTO dto) {

        UserResponseDTO user = userServices.create(dto);

        return ResponseEntity
                .status(201)
                .body(user);
    }

    @Operation(summary = "Atualizar usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @RequestBody @Valid UserUpdateDTO dto) {

        UserResponseDTO user = userServices.update(id, dto);

        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Desativar usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        userServices.delete(id);

        return ResponseEntity.noContent().build();
    }
}

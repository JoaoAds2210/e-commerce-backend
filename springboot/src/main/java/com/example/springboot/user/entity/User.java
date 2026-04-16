package com.example.springboot.user.entity;

import com.example.springboot.address.entity.Address;
import com.example.springboot.product.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    @Pattern(regexp = "^[A-Za-zÀ-ÿ ]+$", message = "Nome deve conter apenas letras")
    @Column(nullable = false)
    private String name;

    @CPF
    @NotBlank
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Email
    @NotBlank
    @Size(max = 150)
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    private LocalDate birthDate;

    @NotBlank
    @Column(nullable = false)
    private String passwordHash;

    @NotBlank
    @Pattern(
            regexp = "^\\d{10,11}$",
            message = "Telefone deve conter 10 ou 11 dígitos"
    )
    @Column(nullable = false, length = 11)
    private String phone;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Address> addresses;

    @PrePersist
    public void prePersist() {
        this.email = email.toLowerCase().trim();
        this.name = name.trim();
        this.phone = phone.replaceAll("\\D", "");
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.email = email.toLowerCase().trim();
        this.name = name.trim();
        this.phone = phone.replaceAll("\\D", "");
    }
}

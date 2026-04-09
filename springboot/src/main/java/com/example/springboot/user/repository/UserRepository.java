package com.example.springboot.user.repository;

import com.example.springboot.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndActiveTrue(String email);

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);

    List<User> findAllByActiveTrue();

    List<User> findAllByActiveFalse();
}

package com.github.FerreirinhaJean.ToDo_App.repository;

import com.github.FerreirinhaJean.ToDo_App.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
}

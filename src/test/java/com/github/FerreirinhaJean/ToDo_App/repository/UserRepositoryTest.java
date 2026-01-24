package com.github.FerreirinhaJean.ToDo_App.repository;

import com.github.FerreirinhaJean.ToDo_App.entity.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.UUID;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should save a new user in database")
    void shouldSaveUser() {
        User user = new User();
        user.setName("Jack Reacher");
        user.setEmail("jack.reacher@gmail.com");
        user.setPassword("{1$NgEf.@yJff6xH");

        userRepository.save(user);

        assertNotNull(user.getId(), "User entity not have Id.");
    }

    @Test
    @Sql("/sql/insert_users.sql")
    void shouldFindUserByEmail() {
        User user = userRepository.findByEmail("john.wick@gmail.com");
        assertNotNull(user);
    }

    @Test
    @Sql("/sql/insert_users.sql")
    void shouldReturnNullInFindUserByEmail() {
        User user = userRepository.findByEmail("james.reece@gmail.com");
        assertNull(user);
    }

    @Test
    @Sql("/sql/insert_users.sql")
    void shouldFindUserById() {
        Optional<User> user = userRepository.findById(UUID.fromString("4d770cb8-67cf-498d-901e-03c541bb38f6"));
        assertThat(user).isPresent();
    }

    @Test
    @Sql("/sql/insert_users.sql")
    void shouldReturnNullInFindUserById() {
        Optional<User> user = userRepository.findById(UUID.fromString("00000000-67cf-498d-901e-03c541bb38f6"));
        assertThat(user).isEmpty();
    }


}

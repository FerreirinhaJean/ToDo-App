package com.github.FerreirinhaJean.ToDo_App.service;

import com.github.FerreirinhaJean.ToDo_App.dto.request.UserRequestDTO;
import com.github.FerreirinhaJean.ToDo_App.entity.User;
import com.github.FerreirinhaJean.ToDo_App.exception.DuplicatedRegisterException;
import com.github.FerreirinhaJean.ToDo_App.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void shouldCreateUser() {
        UserRequestDTO userDTO = new UserRequestDTO(
                "John Snow",
                "john.snow@gmail.com",
                "AegonTargaryen@"
        );
        User user = new User(
                UUID.fromString("27f29e76-4160-462b-981f-844f995bbf76"),
                "John Snow",
                "john.snow@gmail.com",
                "$2a$15$0m2g/gNKxkiFFC3HuTzXGedQSotLMJ8wQyZA/NwxhE2hAjyMDf7gO",
                Instant.now(),
                Instant.now()
        );

        Mockito.when(userRepository.existsByEmail(userDTO.email())).thenReturn(false);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        User userCreated = userService.create(userDTO);

        assertThat(userCreated).isNotNull();
        assertNotNull(userCreated.getId());
        assertEquals(userCreated.getId(), user.getId());

        Mockito.verify(userRepository).save(Mockito.any());
    }

    @Test
    void shouldThrowDuplicatedRegisterExceptionWhenEmailExists() {
        UserRequestDTO userDTO = new UserRequestDTO(
                "Aegon Targaryen",
                "aegon.targaryen@gmail.com",
                "AegonTargaryen@"
        );
        Mockito.when(userRepository.existsByEmail("aegon.targaryen@gmail.com")).thenReturn(true);

        DuplicatedRegisterException exception = assertThrows(DuplicatedRegisterException.class, () -> userService.create(userDTO));
        assertEquals("Email already is used.", exception.getMessage());

        Mockito.verify(userRepository).existsByEmail("aegon.targaryen@gmail.com");
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any());
    }
}

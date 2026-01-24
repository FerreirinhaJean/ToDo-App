package com.github.FerreirinhaJean.ToDo_App.controller;

import com.github.FerreirinhaJean.ToDo_App.dto.request.UserRequestDTO;
import com.github.FerreirinhaJean.ToDo_App.dto.response.UserResponseDTO;
import com.github.FerreirinhaJean.ToDo_App.entity.User;
import com.github.FerreirinhaJean.ToDo_App.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Tag(name = "Users")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(
            summary = "Register",
            description = "Register new users"
    )
    public ResponseEntity<UserResponseDTO> register(
            @RequestBody @Valid UserRequestDTO userRequestDTO
    ) {
        User user = userService.create(userRequestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        UserResponseDTO responseDTO = new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt()
        );

        return ResponseEntity.created(location).body(responseDTO);
    }

}

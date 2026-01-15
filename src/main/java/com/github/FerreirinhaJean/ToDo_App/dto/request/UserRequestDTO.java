package com.github.FerreirinhaJean.ToDo_App.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(

        @NotBlank(message = "The name is required.")
        String name,

        @Email(message = "Invalid email address format.")
        @NotBlank(message = "The email is required.")
        String email,

        @NotBlank(message = "The password is required.")
        String password
) {
}

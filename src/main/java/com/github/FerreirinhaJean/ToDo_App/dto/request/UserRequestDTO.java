package com.github.FerreirinhaJean.ToDo_App.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(

        @NotBlank(message = "The name is required.")
        String name,

        @Email(message = "Invalid email address format.")
        @NotBlank(message = "The email is required.")
        String email,

        @NotBlank(message = "The password is required.")
        @Size(min = 8, max = 32, message = "Password length must be between 8 and 32 characters.")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).+$",
                message = "Password does not meet the required security criteria. It must contain at least one lowercase letter, one uppercase letter, one number, and one special character."
        )
        String password
) {
}

package com.github.FerreirinhaJean.ToDo_App.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TaskCreationRequestDTO(
        @NotBlank
        String title,
        String description
) {
}

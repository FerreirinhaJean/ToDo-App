package com.github.FerreirinhaJean.ToDo_App.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TaskRequestDTO(
        @NotBlank
        String title,
        String description
) {
}

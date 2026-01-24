package com.github.FerreirinhaJean.ToDo_App.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String email,
        @JsonProperty("created_at")
        Instant createdAt
) {
}

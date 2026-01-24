package com.github.FerreirinhaJean.ToDo_App.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ErrorResponseDTO(
        @JsonProperty("status_code")
        int StatusCode,
        String description,
        List<FieldErrorDTO> errors
) {
}

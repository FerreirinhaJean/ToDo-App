package com.github.FerreirinhaJean.ToDo_App.dto.response;

import com.github.FerreirinhaJean.ToDo_App.entity.enums.TaskStatus;

import java.util.UUID;

public record TaskResponseDTO(
        UUID id,
        String title,
        String description,
        TaskStatus status
) {
}

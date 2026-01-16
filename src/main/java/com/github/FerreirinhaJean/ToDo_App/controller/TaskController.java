package com.github.FerreirinhaJean.ToDo_App.controller;

import com.github.FerreirinhaJean.ToDo_App.dto.request.TaskCreationRequestDTO;
import com.github.FerreirinhaJean.ToDo_App.dto.response.TaskResponseDTO;
import com.github.FerreirinhaJean.ToDo_App.entity.Task;
import com.github.FerreirinhaJean.ToDo_App.security.UserPrincipal;
import com.github.FerreirinhaJean.ToDo_App.service.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;


    @PostMapping
    public ResponseEntity<TaskResponseDTO> create(
            @RequestBody @Valid TaskCreationRequestDTO taskCreationRequestDTO,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        Task task = taskService.create(taskCreationRequestDTO, userPrincipal.getEmail());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(task.getId())
                .toUri();

        TaskResponseDTO responseDTO = new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus()
        );

        return ResponseEntity.created(location).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponseDTO>> list(
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "status", required = false) String status,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        Page<Task> tasks = taskService.search(page, size, status, userPrincipal.getEmail());

        Page<TaskResponseDTO> responseDTO = tasks.map(
                task -> new TaskResponseDTO(task.getId(), task.getTitle(), task.getDescription(), task.getStatus()));

        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Void> completeTask(
            @PathVariable(name = "id") String id,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        Task task = taskService.findById(id).orElse(null);
        if (task == null)
            return ResponseEntity.notFound().build();

        if (!task.getUser().getId().equals(userPrincipal.getId()))
            return ResponseEntity.notFound().build();

        taskService.complete(task);
        return ResponseEntity.ok().build();
    }

}

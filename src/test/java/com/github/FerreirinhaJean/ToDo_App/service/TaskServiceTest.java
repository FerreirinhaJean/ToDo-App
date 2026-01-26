package com.github.FerreirinhaJean.ToDo_App.service;

import com.github.FerreirinhaJean.ToDo_App.dto.request.TaskRequestDTO;
import com.github.FerreirinhaJean.ToDo_App.entity.Task;
import com.github.FerreirinhaJean.ToDo_App.entity.User;
import com.github.FerreirinhaJean.ToDo_App.entity.enums.TaskStatus;
import com.github.FerreirinhaJean.ToDo_App.exception.BusinessException;
import com.github.FerreirinhaJean.ToDo_App.repository.TaskRepository;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    UserService userService;

    @Test
    void shouldReturnNewTaskCreated() {
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO(
                "Study C#",
                null
        );
        Task task = new Task(
                UUID.randomUUID(),
                "Study C#",
                null,
                TaskStatus.PENDING,
                Instant.now(),
                Instant.now(),
                new User(
                        UUID.randomUUID(),
                        null,
                        null,
                        null,
                        null,
                        null
                )
        );
        Mockito.when(taskRepository.save(Mockito.any())).thenReturn(task);


        Task taskCreated = taskService.create(taskRequestDTO, UUID.randomUUID());

        assertThat(taskCreated.getId()).isNotNull();
        assertThat(taskCreated.getStatus()).isEqualTo(TaskStatus.PENDING);
        Mockito.verify(taskRepository).save(Mockito.any());
    }

    @Test
    void shouldReturnUpdatedTask() {
        Task task = new Task(
                UUID.randomUUID(),
                "Study C#",
                null,
                TaskStatus.PENDING,
                Instant.now(),
                Instant.now(),
                new User(
                        UUID.randomUUID(),
                        null,
                        null,
                        null,
                        null,
                        null
                )
        );
        Mockito.when(taskRepository.save(task)).thenReturn(task);

        Task taskUpdated = taskService.update(task);

        assertEquals(task.getId(), taskUpdated.getId());
        Mockito.verify(taskRepository).save(task);
    }

    @Test
    void shouldReturnBusinessExceptionWhenIdIsNull() {
        Task task = new Task(
                null,
                "Study C#",
                null,
                TaskStatus.PENDING,
                Instant.now(),
                Instant.now(),
                new User(
                        UUID.randomUUID(),
                        null,
                        null,
                        null,
                        null,
                        null
                )
        );

        BusinessException businessException = assertThrows(BusinessException.class, () -> taskService.update(task));
        assertThat(businessException.getMessage()).isEqualTo("Task id is required to update.");
        Mockito.verify(taskRepository, Mockito.never()).save(task);
    }

    @Test
    void shouldReturnBusinessExceptionWhenStatusIsCompleted() {
        Task task = new Task(
                UUID.randomUUID(),
                "Study C#",
                null,
                TaskStatus.COMPLETED,
                Instant.now(),
                Instant.now(),
                new User(
                        UUID.randomUUID(),
                        null,
                        null,
                        null,
                        null,
                        null
                )
        );

        BusinessException businessException = assertThrows(BusinessException.class, () -> taskService.update(task));
        assertThat(businessException.getMessage()).isEqualTo("Invalid status to update task.");
        Mockito.verify(taskRepository, Mockito.never()).save(task);
    }

    @Test
    void shouldUpdateStatusWhenToPendingCompleteTask() {
        Task task = new Task(
                UUID.randomUUID(),
                "Study C#",
                null,
                TaskStatus.PENDING,
                Instant.now(),
                Instant.now(),
                new User(
                        UUID.randomUUID(),
                        null,
                        null,
                        null,
                        null,
                        null
                )
        );

        taskService.complete(task);

        Mockito.verify(taskRepository).save(task);
    }


    @Test
    void shouldNotUpdatedStatusWhenTaskIsCompleted() {
        Task task = new Task(
                UUID.randomUUID(),
                "Study C#",
                null,
                TaskStatus.COMPLETED,
                Instant.now(),
                Instant.now(),
                new User(
                        UUID.randomUUID(),
                        null,
                        null,
                        null,
                        null,
                        null
                )
        );

        taskService.complete(task);

        Mockito.verify(taskRepository, Mockito.never()).save(task);
    }

    @Test
    void shouldDeleteTask() {
        Task task = new Task(
                UUID.randomUUID(),
                "Study C#",
                null,
                TaskStatus.PENDING,
                Instant.now(),
                Instant.now(),
                new User(
                        UUID.randomUUID(),
                        null,
                        null,
                        null,
                        null,
                        null
                )
        );

        taskService.delete(task);

        Mockito.verify(taskRepository).delete(task);
    }

    @Test
    void ShouldReturnTaskWhenFindById() {
        Task task = new Task(
                UUID.fromString("d1a6d1b2-eca7-4a36-afd4-de5ca6d5ca95"),
                "Study C#",
                null,
                TaskStatus.PENDING,
                Instant.now(),
                Instant.now(),
                new User(
                        UUID.randomUUID(),
                        null,
                        null,
                        null,
                        null,
                        null
                )
        );
        Mockito.when(taskRepository.findByIdAndUserId(Mockito.any(), Mockito.any())).thenReturn(Optional.of(task));

        Optional<Task> taskFound = taskService.findById("d1a6d1b2-eca7-4a36-afd4-de5ca6d5ca95", UUID.randomUUID());

        assertThat(taskFound).isPresent();
        assertThat(taskFound.get().getId()).isEqualTo(task.getId());
        Mockito.verify(taskRepository).findByIdAndUserId(Mockito.any(), Mockito.any());
    }

    @Test
    void ShouldReturnEmptyWhenFindById() {
        Optional<Task> taskFound = taskService.findById("d1a6d1b2-eca7-4a36-afd4-de5ca6d5ca95", UUID.randomUUID());

        assertThat(taskFound).isEmpty();
        Mockito.verify(taskRepository).findByIdAndUserId(Mockito.any(), Mockito.any());
    }

    @Test
    void ShouldReturnEmptyWhenInvalidUUID() {
        Optional<Task> task = taskService.findById("de5ca6d5ca95", UUID.randomUUID());

        assertThat(task).isEmpty();
        Mockito.verify(taskRepository, Mockito.never()).findByIdAndUserId(Mockito.any(), Mockito.any());
    }

}

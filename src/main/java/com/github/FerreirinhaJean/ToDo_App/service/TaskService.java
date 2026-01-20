package com.github.FerreirinhaJean.ToDo_App.service;

import com.github.FerreirinhaJean.ToDo_App.dto.request.TaskRequestDTO;
import com.github.FerreirinhaJean.ToDo_App.entity.Task;
import com.github.FerreirinhaJean.ToDo_App.entity.User;
import com.github.FerreirinhaJean.ToDo_App.entity.enums.TaskStatus;
import com.github.FerreirinhaJean.ToDo_App.exception.BusinessException;
import com.github.FerreirinhaJean.ToDo_App.repository.TaskRepository;
import com.github.FerreirinhaJean.ToDo_App.repository.specs.TaskSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public Task create(TaskRequestDTO taskRequestDTO, UUID userId) {
        User user = userService.findById(userId).orElse(null);

        Task task = new Task();
        task.setTitle(taskRequestDTO.title());

        task.setDescription(formatDescription(taskRequestDTO.description()));
        task.setStatus(TaskStatus.PENDING);
        task.setUser(user);

        return taskRepository.save(task);
    }

    public Page<Task> search(Integer page, Integer size, String status, UUID userId) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Specification<Task> specification = ((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        if (status != null)
            specification = TaskSpecification.statusIsEqual(status);

        User user = userService.findById(userId).orElse(null);
        specification = specification.and(TaskSpecification.userIsEqual(user));
        return taskRepository.findAll(specification, pageRequest);
    }

    public Optional<Task> findById(String id, UUID userId) {
        try {
            return taskRepository.findByIdAndUserId(UUID.fromString(id), userId);
        } catch (IllegalArgumentException illegalArgumentException) {
            return Optional.empty();
        }
    }

    public void complete(Task task) {
        if (task.getStatus() != TaskStatus.COMPLETED) {
            task.setStatus(TaskStatus.COMPLETED);
            taskRepository.save(task);
        }
    }

    public void delete(Task task) {
        taskRepository.delete(task);
    }

    public Task update(Task task) throws BusinessException {
        if (task.getId() == null)
            throw new BusinessException("Task id is required to update.");

        if (task.getStatus() != TaskStatus.PENDING)
            throw new BusinessException("Invalid status to update task.");

        task.setDescription(formatDescription(task.getDescription()));

        return taskRepository.save(task);
    }

    private String formatDescription(String description) {
        return description != null && !description.trim().isEmpty() ? description : null;
    }

}

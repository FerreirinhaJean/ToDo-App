package com.github.FerreirinhaJean.ToDo_App.repository;

import com.github.FerreirinhaJean.ToDo_App.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID>, JpaSpecificationExecutor<Task> {
    Optional<Task> findByIdAndUserId(UUID id, UUID userId);
}

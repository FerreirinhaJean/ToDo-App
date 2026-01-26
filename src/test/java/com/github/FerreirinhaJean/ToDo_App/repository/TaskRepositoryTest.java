package com.github.FerreirinhaJean.ToDo_App.repository;

import com.github.FerreirinhaJean.ToDo_App.entity.Task;
import com.github.FerreirinhaJean.ToDo_App.entity.User;
import com.github.FerreirinhaJean.ToDo_App.entity.enums.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    @Sql(value = {"/sql/insert_users.sql", "/sql/insert_tasks.sql"})
    void shouldReturnTaskWhenFindByIdAndUserId() {
        Optional<Task> task = taskRepository.findByIdAndUserId(
                UUID.fromString("a78ed412-bd89-4942-acf5-7ee4be2452a9"),
                UUID.fromString("4d770cb8-67cf-498d-901e-03c541bb38f6")
        );

        assertThat(task).isPresent();
        assertEquals(task.get().getId(), UUID.fromString("a78ed412-bd89-4942-acf5-7ee4be2452a9"));
    }

    @Test
    @Sql(value = {"/sql/insert_users.sql", "/sql/insert_tasks.sql"})
    void shouldReturnEmptyWhenFindByIdAndUserId() {
        Optional<Task> task = taskRepository.findByIdAndUserId(
                UUID.fromString("a78ed412-bd89-4942-acf5-7ee4be2452a5"),
                UUID.fromString("4d770cb8-67cf-498d-901e-03c541bb38f6")
        );

        assertThat(task).isEmpty();
    }

    @Test
    @Sql("/sql/insert_users.sql")
    void shouldReturnNewTaskCreated() {
        Task task = new Task(
                null,
                "Study C++",
                null,
                TaskStatus.PENDING,
                Instant.now(),
                Instant.now(),
                new User(
                        UUID.fromString("2983de2a-f98b-40bb-9adf-67cb4bdbc471"),
                        null,
                        null,
                        null,
                        null,
                        null
                )
        );

        taskRepository.save(task);

        assertThat(task.getId()).isNotNull();
    }

}

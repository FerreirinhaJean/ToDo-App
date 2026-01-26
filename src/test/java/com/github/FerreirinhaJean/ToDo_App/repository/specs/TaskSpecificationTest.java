package com.github.FerreirinhaJean.ToDo_App.repository.specs;

import com.github.FerreirinhaJean.ToDo_App.entity.Task;
import com.github.FerreirinhaJean.ToDo_App.entity.User;
import com.github.FerreirinhaJean.ToDo_App.entity.enums.TaskStatus;
import com.github.FerreirinhaJean.ToDo_App.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TaskSpecificationTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    @Sql({"/sql/insert_users.sql", "/sql/insert_tasks.sql"})
    void shouldReturnTasksByUser() {
        Specification<Task> specification = ((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
        specification = specification.and(TaskSpecification.userIsEqual(
                new User(
                        UUID.fromString("4d770cb8-67cf-498d-901e-03c541bb38f6"),
                        null,
                        null,
                        null,
                        null,
                        null
                )));

        List<Task> tasks = taskRepository.findAll(specification);

        assertThat(tasks).hasSize(3);
        assertThat(tasks).allMatch(task -> task.getUser().getId().equals(UUID.fromString("4d770cb8-67cf-498d-901e-03c541bb38f6")));
    }

    @Test
    @Sql({"/sql/insert_users.sql", "/sql/insert_tasks.sql"})
    void shouldReturnTasksWhenStatusPending() {
        Specification<Task> specification = ((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
        specification = specification.and(TaskSpecification.statusIsEqual(TaskStatus.PENDING.name()));

        List<Task> tasks = taskRepository.findAll(specification);

        assertThat(tasks).hasSize(3);
        assertThat(tasks).allMatch(task -> task.getStatus().equals(TaskStatus.PENDING));
    }


    @Test
    @Sql({"/sql/insert_users.sql", "/sql/insert_tasks.sql"})
    void shouldReturnTasksWhenStatusCompleted() {
        Specification<Task> specification = ((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
        specification = specification.and(TaskSpecification.statusIsEqual(TaskStatus.COMPLETED.name()));

        List<Task> tasks = taskRepository.findAll(specification);

        assertThat(tasks).hasSize(1);
        assertThat(tasks).allMatch(task -> task.getStatus().equals(TaskStatus.COMPLETED));
    }

}

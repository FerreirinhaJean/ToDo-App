package com.github.FerreirinhaJean.ToDo_App.repository.specs;

import com.github.FerreirinhaJean.ToDo_App.entity.Task;
import com.github.FerreirinhaJean.ToDo_App.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification {

    public static Specification<Task> statusIsEqual(String status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Task> userIsEqual(User user) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("user"), user);
    }

}

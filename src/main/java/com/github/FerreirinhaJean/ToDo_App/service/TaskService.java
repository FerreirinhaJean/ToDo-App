package com.github.FerreirinhaJean.ToDo_App.service;

import com.github.FerreirinhaJean.ToDo_App.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

}

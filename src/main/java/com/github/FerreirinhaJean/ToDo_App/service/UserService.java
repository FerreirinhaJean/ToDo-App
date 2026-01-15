package com.github.FerreirinhaJean.ToDo_App.service;

import com.github.FerreirinhaJean.ToDo_App.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

}

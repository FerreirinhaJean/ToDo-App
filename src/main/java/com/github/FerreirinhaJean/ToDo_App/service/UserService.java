package com.github.FerreirinhaJean.ToDo_App.service;

import com.github.FerreirinhaJean.ToDo_App.dto.request.UserRequestDTO;
import com.github.FerreirinhaJean.ToDo_App.entity.User;
import com.github.FerreirinhaJean.ToDo_App.exception.DuplicatedRegisterException;
import com.github.FerreirinhaJean.ToDo_App.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(UserRequestDTO userRequestDTO) throws DuplicatedRegisterException {
        if (userRepository.existsByEmail(userRequestDTO.email()))
            throw new DuplicatedRegisterException("Email already is used.");

        User user = new User();
        user.setName(userRequestDTO.name());
        user.setEmail(userRequestDTO.email());
        user.setPassword(passwordEncoder.encode(userRequestDTO.password()));

        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}

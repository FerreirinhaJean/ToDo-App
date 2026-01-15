package com.github.FerreirinhaJean.ToDo_App.security;

import com.github.FerreirinhaJean.ToDo_App.entity.User;
import com.github.FerreirinhaJean.ToDo_App.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthenticationProvider implements UserDetailsService {

    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);

        if (user == null)
            throw new UsernameNotFoundException("email and/or password is incorrect.");

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getName())
                .password(user.getPassword())
                .build();
    }
}

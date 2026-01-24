package com.github.FerreirinhaJean.ToDo_App.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        UserPrincipal principal = new UserPrincipal(
                UUID.fromString(jwt.getSubject()),
                jwt.getClaim("name"),
                jwt.getClaim("email"),
                null
        );

        return new UsernamePasswordAuthenticationToken(
                principal,
                jwt,
                List.of()
        );
    }
}

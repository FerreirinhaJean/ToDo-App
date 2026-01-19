package com.github.FerreirinhaJean.ToDo_App.config;

import com.github.FerreirinhaJean.ToDo_App.security.JwtAuthConverter;
import com.github.FerreirinhaJean.ToDo_App.security.UserPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer();

        httpSecurity.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(authorizationServerConfigurer, (oAuth2AuthorizationServerConfigurer ->
                        oAuth2AuthorizationServerConfigurer.oidc(Customizer.withDefaults())))
                .authorizeHttpRequests(authorizer -> {
                    authorizer.anyRequest().authenticated();
                }).exceptionHandling(
                        exceptionHandling ->
                                exceptionHandling.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")));

        return httpSecurity.build();
    }

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    public SecurityFilterChain defaultSecurityFilterChain(
            HttpSecurity httpSecurity,
            JwtAuthConverter jwtAuthConverter
    ) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizer -> {
                    authorizer.requestMatchers(HttpMethod.POST, "/auth/register").permitAll();
                    authorizer.anyRequest().authenticated();
                })
                .formLogin(form -> form.loginPage("/login").permitAll())
                .oauth2ResourceServer(
                        resourceServer ->
                                resourceServer.jwt(
                                        jwtConfigurer ->
                                                jwtConfigurer.jwtAuthenticationConverter(jwtAuthConverter)))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(15);
    }


    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        return context -> {
            if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
                UserPrincipal userPrincipal = (UserPrincipal) context.getPrincipal().getPrincipal();

                context.getClaims().claim("email", userPrincipal.getEmail());
                context.getClaims().subject(userPrincipal.getId().toString());
                context.getClaims().claim("name", userPrincipal.getName());
            }
        };
    }

}

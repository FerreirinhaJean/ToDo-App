package com.github.FerreirinhaJean.ToDo_App.controller;

import com.github.FerreirinhaJean.ToDo_App.entity.User;
import com.github.FerreirinhaJean.ToDo_App.exception.DuplicatedRegisterException;
import com.github.FerreirinhaJean.ToDo_App.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.util.UUID;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @MockitoBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnStatusCreated() throws Exception {
        User user = new User(
                UUID.fromString("540ac6d0-20fb-4652-8a8f-a918248f9310"),
                "Jack Reacher",
                "jack.reacher@gmail.com",
                "JackReacher@123",
                Instant.now(),
                Instant.now()
        );
        Mockito.when(userService.create(Mockito.any())).thenReturn(user);

        String body = """
                {
                    "name": "Jack Reacher",
                    "email": "jack.reacher@gmail.com",
                    "password": "JackReacher@123"
                }
                """;

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        );

        resultActions
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("540ac6d0-20fb-4652-8a8f-a918248f9310"));
    }

    @Test
    void shouldReturnStatusConflictByEmailAlreadyUsed() throws Exception {
        User user = new User(
                UUID.fromString("540ac6d0-20fb-4652-8a8f-a918248f9310"),
                "Jack Reacher",
                "jack.reacher@gmail.com",
                "JackReacher@123",
                Instant.now(),
                Instant.now()
        );
        Mockito.when(userService.create(Mockito.any())).thenThrow(DuplicatedRegisterException.class);

        String body = """
                {
                    "name": "Jack Reacher",
                    "email": "jack.reacher@gmail.com",
                    "password": "JackReacher@123"
                }
                """;

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        );

        resultActions
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status_code").value(HttpStatus.CONFLICT.value()));
    }

    @Test
    void shouldReturnStatusUnprocessableEntityAndInvalidPassword() throws Exception {
        String body = """
                {
                    "name": "Jack Reacher",
                    "email": "jack.reacher@gmail.com",
                    "password": "12345678"
                }
                """;

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        );

        resultActions
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status_code").value(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("password"));

        Mockito.verify(userService, Mockito.never()).create(Mockito.any());
    }


}

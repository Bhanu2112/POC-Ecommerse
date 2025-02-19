package com.register.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.register.service.dto.RegisterRequest;
import com.register.service.entity.Role;
import com.register.service.entity.User;
import com.register.service.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)  // Enables Mockito annotations
@WebMvcTest(UserController.class)    // Loads only UserController (lighter test)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;  // Mocked dependency

    @InjectMocks
    private UserController userController; // Injecting mocks into the controller

    @BeforeEach
    void setUp() {
        // Manually build MockMvc with the controller
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }


    @Test
    void registerUser() throws Exception {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("bhanu");
        registerRequest.setEmail("bhanu@.com");
        registerRequest.setPassword("password");
        registerRequest.setRole("USER");

        User expectedUser = new User();
        expectedUser.setUsername("bhanu");
        expectedUser.setEmail("bhanu@.com");
        expectedUser.setPassword("password");
        expectedUser.setRole(Role.valueOf("USER"));

        when(userService.registerUser(any(RegisterRequest.class))).thenReturn(expectedUser);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("bhanu"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("bhanu@.com"));

        Mockito.verify(userService).registerUser(any(RegisterRequest.class));
    }


    @Test
    void findByUsername() throws Exception {
        // Arrange
        String username = "bhanu";
        User expectedUser = new User();
        expectedUser.setUsername(username);
        expectedUser.setEmail("bhanu@.com");

        when(userService.findByUsername(username)).thenReturn(expectedUser);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/user/byusername/{username}", username))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("bhanu"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("bhanu@.com"));

        Mockito.verify(userService).findByUsername(username);
    }


    @Test
    void welcome() throws Exception {
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/user/wel"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("welcome"));
    }
}

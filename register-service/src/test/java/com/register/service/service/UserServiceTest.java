package com.register.service.service;

import com.register.service.dto.AddressDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.register.service.dto.RegisterRequest;
import com.register.service.entity.Address;
import com.register.service.entity.Role;
import com.register.service.entity.User;
import com.register.service.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void testRegisterUser() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testUser");
        request.setEmail("test@example.com");
        request.setPassword("password");
        request.setRole(Role.USER.toString());
        AddressDTO address = new AddressDTO();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        request.setAddresses(new ArrayList<>());
        request.getAddresses().add(address);




        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(Role.valueOf(request.getRole()));
        user.setAddress(request.getAddresses().stream().map(addressDTO -> {
            Address address1 = new Address();
            address1.setStreet(addressDTO.getStreet());
            address1.setCity(addressDTO.getCity());
            address1.setState(addressDTO.getState());
            return address1;
        }).collect(Collectors.toList()));

        when(userRepository.save(any(User.class))).thenReturn(user);
        // Act
        User outputuser = userService.registerUser(request);

        // Assert
        assertNotNull(user);
        assertEquals("testUser", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
        assertEquals(Role.USER, user.getRole());
        assertEquals(1, user.getAddress().size());
        assertEquals("123 Main St", user.getAddress().get(0).getStreet());
    }

    @Test
    public void testFindByUsername() {
        // Arrange
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        User foundUser = userService.findByUsername(username);

        // Assert
        assertNotNull(foundUser);
        assertEquals(username, foundUser.getUsername());
    }

    @Test
    public void testFindByUsernameNotFound() {
        // Arrange
        String username = "nonExistentUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> userService.findByUsername(username));
    }
}
package com.backend.services;

import com.backend.Config.JWTUtils;
import com.backend.dto.UserDto;
import com.backend.model.User;
import com.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository usersRepo;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto register(UserDto registrationRequest) {
        UserDto response = new UserDto();

        try {
            User newUser = User.builder()
                    .firstName(registrationRequest.getFirstName())
                    .lastName(registrationRequest.getLastName())
                    .email(registrationRequest.getEmail())
                    .password(passwordEncoder.encode(registrationRequest.getPassword()))
                    .build();

            User savedUser = usersRepo.save(newUser);
            response = mapToDto(savedUser);
            response.setMessage("User registered successfully");
        } catch (Exception e) {
            response.setError("Registration failed: " + e.getMessage());
        }

        return response;
    }

    public UserDto login(UserDto loginRequest) {
        UserDto response = new UserDto();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            User user = usersRepo.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String jwt = jwtUtils.generateToken(user, user.getEmail());
            String refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

            response = mapToDto(user);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setMessage("Successfully logged in");
        } catch (Exception e) {
            response.setError("Login failed: " + e.getMessage());
        }

        return response;
    }

    public UserDto refreshToken(UserDto refreshTokenRequest) {
        UserDto response = new UserDto();

        try {
            String email = jwtUtils.extractUsername(refreshTokenRequest.getToken());
            User user = usersRepo.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), user)) {
                String newToken = jwtUtils.generateToken(user, user.getEmail());
                response.setToken(newToken);
                response.setRefreshToken(refreshTokenRequest.getToken());
                response.setMessage("Token refreshed successfully");
            } else {
                response.setError("Invalid refresh token");
            }
        } catch (Exception e) {
            response.setError("Token refresh failed: " + e.getMessage());
        }

        return response;
    }

    public List<UserDto> getAllUsers() {
        List<User> users = usersRepo.findAll();
        return users.stream().map(this::mapToDto).toList();
    }

    public UserDto getUserById(Long id) {
        UserDto response = new UserDto();

        try {
            User user = usersRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            response = mapToDto(user);
        } catch (Exception e) {
            response.setError("Error retrieving user: " + e.getMessage());
        }

        return response;
    }

    public UserDto deleteUser(Long userId) {
        UserDto response = new UserDto();

        try {
            if (usersRepo.existsById(userId)) {
                usersRepo.deleteById(userId);
                response.setMessage("User deleted successfully");
            } else {
                response.setError("User not found for deletion");
            }
        } catch (Exception e) {
            response.setError("Error deleting user: " + e.getMessage());
        }

        return response;
    }

    public UserDto updateUser(Long userId, UserDto updatedUserDto) {
        UserDto response = new UserDto();

        try {
            User existingUser = usersRepo.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found for update"));

            existingUser.setEmail(updatedUserDto.getEmail());
            existingUser.setFirstName(updatedUserDto.getFirstName());
            existingUser.setLastName(updatedUserDto.getLastName());

            if (updatedUserDto.getPassword() != null && !updatedUserDto.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(updatedUserDto.getPassword()));
            }

            User savedUser = usersRepo.save(existingUser);
            response = mapToDto(savedUser);
            response.setMessage("User updated successfully");
        } catch (Exception e) {
            response.setError("Error updating user: " + e.getMessage());
        }

        return response;
    }

    public UserDto getMyInfo(String email) {
        UserDto response = new UserDto();

        try {
            User user = usersRepo.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            response = mapToDto(user);
        } catch (Exception e) {
            response.setError("Error retrieving user info: " + e.getMessage());
        }

        return response;
    }

    private UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}

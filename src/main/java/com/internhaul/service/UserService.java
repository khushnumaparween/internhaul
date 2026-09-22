package com.internhaul.service;

import com.internhaul.dto.SignupRequest;
import com.internhaul.entity.User;
import com.internhaul.entity.UserRole;
import com.internhaul.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerManager(SignupRequest request) {

        if (userRepository.existsByUsername(
                request.getUsername())) {

            throw new RuntimeException(
                    "Username already exists");
        }

        if (userRepository.existsByEmail(
                request.getEmail())) {

            throw new RuntimeException(
                    "Email already exists");
        }

        if (request.getPassword() == null ||
                request.getPassword().length() < 8) {

            throw new RuntimeException(
                    "Password must contain at least 8 characters");
        }

        User user = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .role(UserRole.MANAGER)
                .enabled(true)
                .build();

        return userRepository.save(user);
    }
}
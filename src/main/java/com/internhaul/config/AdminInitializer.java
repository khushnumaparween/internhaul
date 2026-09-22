package com.internhaul.config;

import com.internhaul.entity.User;
import com.internhaul.entity.UserRole;
import com.internhaul.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer
        implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (userRepository.existsByUsername("admin")) {
            return;
        }

        User admin = User.builder()
                .name("System Administrator")
                .username("admin")
                .email("admin@internhaul.com")
                .password(
                        passwordEncoder.encode(
                                "admin12345"
                        )
                )
                .role(UserRole.ADMIN)
                .enabled(true)
                .build();

        userRepository.save(admin);
    }
}
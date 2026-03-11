package com.example.Server4;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminUsername = "admin";
            String adminEmail = "admin@test.com";
            String adminPassword = "123456";

            // Nếu admin đã tồn tại (username hoặc email) thì bỏ qua
            if (userRepository.existsByUsername(adminUsername) || userRepository.existsByEmail(adminEmail)) {
                return;
            }

            User admin = new User();
            admin.setUsername(adminUsername);
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole(User.Role.ADMIN);

            userRepository.save(admin);

            System.out.println(" Seeded ADMIN user: admin / 123456");
        };
    }
}

package com.ntd.socialnetwork.security.configuration;

import com.ntd.socialnetwork.user.service.UserService;
import com.ntd.socialnetwork.user.enums.Role;
import com.ntd.socialnetwork.user.model.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;
    UserService userService;

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            var roles = new HashSet<String>();
            roles.add(Role.ADMIN.name());

            User existingAdmin = userService.getUserByUsername("admin");

            if (existingAdmin == null) {
                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
//                        .roles(roles)
                        .email("admin@gmail.com")
                        .firstname("System")
                        .lastname("Admin")
                        .phone("0853845969")
                        .build();
                userService.saveUser(user);
                log.warn("Admin account has been created. Please change the default password: admin");
            } else {
                log.info("Admin user already exists: {}", existingAdmin.getUsername());
            }
        };
    }

}

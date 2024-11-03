package com.wedsite.zuong2004.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.wedsite.zuong2004.enity.Role;
import com.wedsite.zuong2004.enity.User;
import com.wedsite.zuong2004.enums.RolePlay;
import com.wedsite.zuong2004.repository.UserRepository;

@Configuration
public class ApplicationInitConfig {

    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ApplicationInitConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            if (userRepository.findByPhoneNumber("admin").isEmpty()) {
                Role role = new Role();
                role.setId(2L);
                role.setName(RolePlay.ADMIN.name());
                User user = User.builder()
                        .phoneNumber("admin")
                        .password(passwordEncoder.encode("admin"))
                        .role(role)
                        .build();

                userRepository.save(user);

            }
            ;
        };
    }
}

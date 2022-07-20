package com.shohag.Backend.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordGenerator implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;

    public PasswordGenerator(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("bcryptPassword: " + this.passwordEncoder.encode("xyz"));
    }
}

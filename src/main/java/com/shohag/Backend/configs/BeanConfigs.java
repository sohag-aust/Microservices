package com.shohag.Backend.configs;

import com.shohag.Backend.entities.Student;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfigs {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean("student1")
    public Student getStudent1() {
        return new Student(1L, "Student-1");
    }

    @Bean("student2")
    @Primary
    public Student getStudent2() {
        return new Student(2L, "Student-2");
    }
}

package com.example.mvcdemo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {

            Student nicholas = new Student(
                    "Seguya Nicholas",
                    "seguyanicholus@yahoo.com",
                    LocalDate.of(1988, Month.NOVEMBER,6)
            );

            Student sylvia = new Student(
                    "Nakandi Sylvia",
                    "nakawilliams@gmail.com",
                    LocalDate.of(1989, Month.JUNE,13)
            );

            repository.saveAll(List.of(nicholas, sylvia));
        };

    }
}

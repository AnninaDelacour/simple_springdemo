package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;


@Configuration
public class StudentConfig {

    @Bean // What and why @Beans in Spring: https://www.baeldung.com/spring-bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student mariam = new Student(
                    "Mariam",
                    LocalDate.of(1995, Month.DECEMBER, 5),
                    "mariam.jamal@gmail.com"
            );

            Student alexa = new Student(
                    "Alexa",
                    LocalDate.of(1992, Month.APRIL, 16),
                    "alexa.pitts@gmail.com"
            );

            repository.saveAll(
                    List.of(mariam,alexa)
            );
        };
    }
}

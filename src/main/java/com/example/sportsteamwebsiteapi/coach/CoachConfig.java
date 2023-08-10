package com.example.sportsteamwebsiteapi.coach;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class CoachConfig {
    @Bean
    CommandLineRunner coachCommandLineRunner(CoachRepository repository) {
        return args -> {
            repository.saveAll(
                List.of(
                    new Coach(
                        "Tudor-Marius",
                        "Orasanu",
                        "Romanian",
                        LocalDate.of(1984, 8, 14),
                        "Head Coach",
                        new ClassPathResource("static/images/orosanu_tudor.png")
                            .getInputStream()
                            .readAllBytes()
                    ),
                    new Coach(
                        "Vasile",
                        "Mosuc",
                        "Romanian",
                        LocalDate.of(1978, 7, 6),
                        "Assistant Coach",
                        new ClassPathResource("static/images/mosuc_vasile.png")
                            .getInputStream()
                            .readAllBytes()
                    )
                )
            );
        };
    }
}
package com.example.sportsteamwebsiteapi.player;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class PlayerConfig {
    @Bean
    CommandLineRunner playerCommandLineRunner(PlayerRepository repository) {
        return args -> {
            repository.saveAll(
                List.of(
                    new Player(
                        "Lucian",
                        "Tofan",
                        "Romanian",
                        "Opposite",
                        LocalDate.of(1984, 10, 23),
                        2.00,
                        1,
                        "",
                        new ClassPathResource("static/images/tofan_lucian.png")
                            .getInputStream()
                            .readAllBytes(),
                        new ArrayList<>()
                    ),
                    new Player(
                        "Darius",
                        "Pop",
                        "Romanian",
                        "Opposite",
                        LocalDate.of(2003, 3, 10),
                        2.00,
                        2,
                        "",
                        new ClassPathResource("static/images/pop_darius.png")
                            .getInputStream()
                            .readAllBytes(),
                        new ArrayList<>()
                    ),
                    new Player(
                        "Ioan",
                        "Verciuc",
                        "Romanian",
                        "Outside Hitter",
                        LocalDate.of(2008, 1, 23),
                        1.90,
                        4,
                        "",
                        new ClassPathResource("static/images/verciuc_ioan.png")
                            .getInputStream()
                            .readAllBytes(),
                        new ArrayList<>()
                    ),
                    new Player(
                        "Alexandru",
                        "Roman",
                        "Romanian",
                        "Outside Hitter",
                        LocalDate.of(2002, 12, 5),
                        2.00,
                        5,
                        "",
                        new ClassPathResource("static/images/roman_alexandru.png")
                            .getInputStream()
                            .readAllBytes(),
                        new ArrayList<>()
                    ),
                    new Player(
                        "Andrei",
                        "Sasu",
                        "Romanian",
                        "Setter",
                        LocalDate.of(1986, 7, 6),
                        1.84,
                        6,
                        "",
                        new ClassPathResource("static/images/sasu_andrei.png")
                            .getInputStream()
                            .readAllBytes(),
                        new ArrayList<>()
                    ),
                    new Player(
                        "Sabin",
                        "Hortopeanu",
                        "Romanian",
                        "Middle-Blocker",
                        LocalDate.of(2005, 3, 23),
                        2.00,
                        7,
                        "",
                        new ClassPathResource("static/images/hortopeanu_sabin.png")
                            .getInputStream()
                            .readAllBytes(),
                        new ArrayList<>()
                    ),
                    new Player(
                        "Marius",
                        "Gontariu",
                        "Romanian",
                        "Outside Hitter",
                        LocalDate.of(1992, 8, 5),
                        1.94,
                        9,
                        "",
                        new ClassPathResource("static/images/gontariu_marius.png")
                            .getInputStream()
                            .readAllBytes(),
                        new ArrayList<>()
                    ),
                    new Player(
                        "Matei",
                        "Platon",
                        "Romanian",
                        "Libero",
                        LocalDate.of(2003, 7, 27),
                        1.85,
                        10, "",
                        new ClassPathResource("static/images/platon_matei.png")
                            .getInputStream()
                            .readAllBytes(),
                        new ArrayList<>()
                    ),
                    new Player(
                        "Cosmin",
                        "Boghean",
                        "Romanian",
                        "Middle-Blocker",
                        LocalDate.of(2002, 7, 17),
                        1.90,
                        11,
                        "",
                        new ClassPathResource("static/images/boghean_cosmin.png")
                            .getInputStream()
                            .readAllBytes(),
                        new ArrayList<>()
                    ),
                    new Player(
                        "Alexandru",
                        "Dragomir",
                        "Romanian",
                        "Setter",
                        LocalDate.of(1999, 7, 2),
                        1.95,
                        12,
                        "",
                        new ClassPathResource("static/images/dragomir_alexandru.png")
                            .getInputStream()
                            .readAllBytes(),
                        new ArrayList<>()
                    ),
                    new Player(
                        "Cezar",
                        "Ciubotariu",
                        "Romanian",
                        "Middle-Blocker",
                        LocalDate.of(2004, 7, 28),
                        1.90,
                        13,
                        "",
                        new ClassPathResource("static/images/ciubotariu_cezar.png")
                            .getInputStream()
                            .readAllBytes(),
                        new ArrayList<>()
                    ),
                    new Player(
                        "Cosmin",
                        "Ciubotaru",
                        "Romanian",
                        "Middle-blocker",
                        LocalDate.of(2000, 10, 18),
                        2.05,
                        14,
                        "",
                        new ClassPathResource("static/images/ciubotaru_cosmin.png")
                            .getInputStream()
                            .readAllBytes(),
                        new ArrayList<>()
                    ),
                    new Player(
                        "Andrei",
                        "Curic",
                        "Romanian",
                        "Libero",
                        LocalDate.of(2004, 5, 2),
                        1.73,
                        15,
                        "",
                        new ClassPathResource("static/images/curic_andrei.png")
                            .getInputStream()
                            .readAllBytes(),
                        new ArrayList<>()
                    ),
                    new Player(
                        "Alexandru",
                        "Andrusca",
                        "Romanian",
                        "Wing Spiker",
                        LocalDate.of(2007, 1, 17),
                        1.83,
                        16,
                        "",
                        new ClassPathResource("static/images/andrusca_alexandru.png")
                            .getInputStream()
                            .readAllBytes(),
                        new ArrayList<>()
                    )
                )
            );
        };
    }
}

package com.example.sportsteamwebsiteapi.junior;

import com.example.sportsteamwebsiteapi.juniorAchievement.JuniorAchievement;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Junior {
    @Id
    @SequenceGenerator(
        name = "juniors_sequence",
        sequenceName = "juniors_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "juniors_sequence"
    )
    private Integer juniorId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String division;
    @JsonIgnore
    @OneToMany(mappedBy = "junior", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("achievementDate DESC")
    private List<JuniorAchievement> juniorAchievements;
}

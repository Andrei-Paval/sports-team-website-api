package com.example.sportsteamwebsiteapi.juniorAchievement;

import com.example.sportsteamwebsiteapi.junior.Junior;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class JuniorAchievement {
    @Id
    @SequenceGenerator(
        name = "junior_achievement_sequence",
        sequenceName = "junior_achievement_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "junior_achievement_sequence"
    )
    private Integer juniorAchievementId;
    private String achievement;
    private LocalDate achievementDate;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "junior_id")
    private Junior junior;
}

package com.example.sportsteamwebsiteapi.playerAchievement;

import com.example.sportsteamwebsiteapi.player.Player;
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
public class PlayerAchievement {
    @Id
    @SequenceGenerator(
        name = "player_achievement_sequence",
        sequenceName = "player_achievement_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "player_achievement_sequence"
    )
    private Integer playerAchievementId;
    private String achievement;
    private LocalDate achievementDate;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;
}

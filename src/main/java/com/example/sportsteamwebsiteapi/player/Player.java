package com.example.sportsteamwebsiteapi.player;

import com.example.sportsteamwebsiteapi.playerAchievement.PlayerAchievement;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Player {
    @Id
    @SequenceGenerator(
        name = "player_sequence",
        sequenceName = "player_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "player_sequence"
    )
    private Integer playerId;
    private String firstName;
    private String lastName;
    private String nationality;
    private String position;
    private LocalDate birthDate;
    private Double height;
    @Column(unique = true)
    private int playerNumber;
    private String description;
    @JsonIgnore
    @Lob
    private byte[] photo;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("achievementDate DESC")
    private List<PlayerAchievement> playerAchievements;
    
    public Player(
        String firstName, String lastName, String nationality, String position,
        LocalDate birthDate, Double height, int playerNumber, String description,
        byte[] photo, List<PlayerAchievement> playerAchievements
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.position = position;
        this.birthDate = birthDate;
        this.height = height;
        this.playerNumber = playerNumber;
        this.description = description;
        this.photo = photo;
        this.playerAchievements = playerAchievements;
    }
}

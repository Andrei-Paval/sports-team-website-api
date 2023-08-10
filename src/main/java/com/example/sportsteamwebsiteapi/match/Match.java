package com.example.sportsteamwebsiteapi.match;

import com.example.sportsteamwebsiteapi.competition.Competition;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Match {
    @Id
    @SequenceGenerator(
        name = "matches_sequence",
        sequenceName = "matches_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "matches_sequence"
    )
    private int matchId;
    @Lob
    private byte[] homeTeamLogo;
    private String homeTeam;
    @Lob
    private byte[] awayTeamLogo;
    private String awayTeam;
    private LocalDate matchDate;
    private String matchLocation;
    private String livestreamUrl;
    private String matchScore;
    private String set1Score;
    private String set2Score;
    private String set3Score;
    private String set4Score;
    private String set5Score;
    @ManyToOne
    @JoinColumn(name = "competition_id")
    private Competition competition;
}

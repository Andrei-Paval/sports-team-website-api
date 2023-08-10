package com.example.sportsteamwebsiteapi.competition;

import com.example.sportsteamwebsiteapi.match.Match;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"competitionName", "season"})
    }
)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Competition {
    @Id
    @SequenceGenerator(
        name = "competitions_sequence",
        sequenceName = "competitions_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "competitions_sequence"
    )
    private int competitionId;
    private String competitionName;
    private String season;
    @JsonIgnore
    @OneToMany(
        mappedBy = "competition",
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL, orphanRemoval = true
    )
    @OrderBy("matchDate ASC")
    private List<Match> matches;
}
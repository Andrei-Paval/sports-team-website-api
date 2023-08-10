package com.example.sportsteamwebsiteapi.coach;

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
public class Coach {
    @Id
    @SequenceGenerator(
        name = "coach_sequence",
        sequenceName = "coach_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "coach_sequence"
    )
    private Integer coachId;
    private String firstName;
    private String lastName;
    private String nationality;
    private LocalDate birthDate;
    private String description;
    @JsonIgnore
    @Lob
    private byte[] photo;
    
    public Coach(
        String firstName, String lastName, String nationality,
        LocalDate birthDate, String description, byte[] photo
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.birthDate = birthDate;
        this.description = description;
        this.photo = photo;
    }
}

package com.example.sportsteamwebsiteapi.sponsor;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Sponsor {
    @Id
    @SequenceGenerator(
        name = "sponsor_sequence",
        sequenceName = "sponsor_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "sponsor_sequence"
    )
    private Integer sponsorId;
    private String sponsorName;
    private String season;
    private String sponsorWebsite;
    @Lob
    private byte[] sponsorLogo;
}

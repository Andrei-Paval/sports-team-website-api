package com.example.sportsteamwebsiteapi.attachment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Attachment {
    @Id
    @SequenceGenerator(
        name = "attachment_sequence",
        sequenceName = "attachment_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "attachment_sequence"
    )
    private Integer attachmentId;
    @JsonIgnore
    private String attachmentType;
    private String videoUrl;
    private byte[] image;
}
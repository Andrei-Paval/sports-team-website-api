package com.example.sportsteamwebsiteapi.draft;

import com.example.sportsteamwebsiteapi.attachment.Attachment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Draft {
    @Id
    @SequenceGenerator(
        name = "draft_sequence",
        sequenceName = "draft_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "draft_sequence"
    )
    private Integer draftId;
    private String draftTitle;
    @Column(length = 5000)
    private String description;
    private String hashtags;
    private LocalDateTime draftDate;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments;
}

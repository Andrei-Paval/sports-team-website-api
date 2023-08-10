package com.example.sportsteamwebsiteapi.news;

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
public class News {
    @Id
    @SequenceGenerator(
        name = "news_sequence",
        sequenceName = "news_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "news_sequence"
    )
    private Integer newsId;
    private String newsTitle;
    @Column(length = 5000)
    private String description;
    private LocalDateTime publicationDate;
    private String hashtags;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments;
}

package com.example.newpointviewapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Subreddit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subredditId;

    @NotBlank(message = "Subreddit name mustn't be empty !")
    private String name;

    @NotBlank(message = "Subreddit description mustn't be empty !")
    private String description;

    @OneToMany
    private List<Post> posts ;

    private Instant createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;



}

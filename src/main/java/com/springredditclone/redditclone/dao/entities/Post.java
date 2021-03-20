package com.springredditclone.redditclone.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Data // lombok => to generate getter & setter
@Builder // to generate buildr methods for our class ( user builder design pattern to create object)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  long postId;

    @NotBlank(message = "post name cann't be empty or null")
    private String postName;

    @Nullable
    private String url;

    @Nullable
    @Lob
    private String description;

    private Integer voteCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "userId")
    private User user;

    private Instant createdDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subreddit_id",referencedColumnName = "id")
    private Subreddit subreddit;



}

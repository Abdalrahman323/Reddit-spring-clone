package com.springredditclone.redditclone.controller;

import com.springredditclone.redditclone.dto.SubredditDTO;
import com.springredditclone.redditclone.service.SubredditService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
public class SubredditController {

    final private SubredditService subredditService;

    @PostMapping
    public ResponseEntity<SubredditDTO> createSubreddit(@RequestBody SubredditDTO subredditDTO){

        SubredditDTO savedSubreddit = subredditService.save(subredditDTO);

        return new ResponseEntity<>(savedSubreddit, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SubredditDTO>> getAllSubreddits(){
        List<SubredditDTO> allSubreddits = subredditService.getAll();

        return new ResponseEntity<>(allSubreddits,HttpStatus.OK);
    }
}

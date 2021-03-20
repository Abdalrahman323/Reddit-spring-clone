package com.springredditclone.redditclone.controller;

import com.springredditclone.redditclone.dto.PostRequest;
import com.springredditclone.redditclone.dto.PostResponse;
import com.springredditclone.redditclone.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest){
        postService.save(postRequest);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(){
        return new ResponseEntity<>(postService.getAllPosts(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id){
        return new ResponseEntity<>(postService.getPost(id),HttpStatus.OK);
    }



    @GetMapping("/by-subreddit/{id}")
     public ResponseEntity<List<PostResponse>> getPostsBySubreddit(@PathVariable Long id){
        return new ResponseEntity<>(postService.getPostsBySubreddit(id),HttpStatus.OK);
    }

    @GetMapping("/by-user/{username}")
    public List<PostResponse> getPostsByUsername(@PathVariable  String username){
        return postService.getPostsByUsername(username);
    }
}

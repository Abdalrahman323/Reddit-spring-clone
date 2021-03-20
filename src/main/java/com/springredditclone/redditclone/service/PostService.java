package com.springredditclone.redditclone.service;

import com.springredditclone.redditclone.dao.entities.Post;
import com.springredditclone.redditclone.dao.entities.Subreddit;
import com.springredditclone.redditclone.dao.entities.User;
import com.springredditclone.redditclone.dao.repository.PostRepository;
import com.springredditclone.redditclone.dao.repository.SubredditRepository;
import com.springredditclone.redditclone.dto.PostRequest;
import com.springredditclone.redditclone.dto.PostResponse;
import com.springredditclone.redditclone.exceptions.PostNotFoundException;
import com.springredditclone.redditclone.mapper.PostMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class PostService {

    private final PostMapper postMapper;
    private final SubredditRepository subredditRepository;
    private final PostRepository postRepository;
    private final AuthService authService;


    public void save(PostRequest postRequest) {


        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new PostNotFoundException("there is subreddit with name " + postRequest.getPostName()));

        postRepository.save( postMapper.mapDtoToPost(postRequest, subreddit, authService.getCurrentUser()));

    }

    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new PostNotFoundException(id.toString()));
        return  postMapper.mapToDto(post);
    }

    public List<PostResponse> getAllPosts() {
       return  postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<PostResponse> getPostsBySubreddit(Long id) {

        Optional<List<Post>> posts = postRepository.findAllBySubreddit_Id(id);

        return posts.get().
                stream().
                map(postMapper::mapToDto)
                .collect(Collectors.toList());
//        Subreddit subreddit = subredditRepository.findById(subredditId)
//                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
//        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
//        return posts.stream().map(postMapper::mapToDto).collect(toList());

    }

    public List<PostResponse> getPostsByUsername(String username) {

        Optional<List<Post>> posts = postRepository.findAllByUser_Username(username);

        return posts.get()
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }
}

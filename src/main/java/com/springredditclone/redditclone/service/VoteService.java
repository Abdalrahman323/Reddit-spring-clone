package com.springredditclone.redditclone.service;

import com.springredditclone.redditclone.dao.entities.Post;
import com.springredditclone.redditclone.dao.entities.Vote;
import com.springredditclone.redditclone.dao.repository.PostRepository;
import com.springredditclone.redditclone.dao.repository.VoteRepository;
import com.springredditclone.redditclone.dto.VoteDto;
import com.springredditclone.redditclone.exceptions.PostNotFoundException;
import com.springredditclone.redditclone.exceptions.SpringRedditException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.springredditclone.redditclone.dao.entities.VoteType.UPVOTE;

import java.util.Optional;

@AllArgsConstructor
@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final  AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {

        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post not found with ID -" + voteDto.getPostId()));
        Optional<Vote> voteByUserAndPost = voteRepository.findTopByPost_PostIdAndUser_UserIdOrderByVoteIdDesc(voteDto.getPostId(), authService.getCurrentUser().getUserId());

        // validation to ban user to upvote or downvote the post twice
        if(voteByUserAndPost.isPresent() && voteByUserAndPost.get().getVoteType().equals(voteDto.getVoteType())){
            throw  new SpringRedditException("You have already "+voteDto.getVoteType()+" for this post");
        }

        if(UPVOTE.equals(voteDto.getVoteType())){
            post.setVoteCount(post.getVoteCount() + 1);
        } else{
            post.setVoteCount(post.getVoteCount() - 1);
        }

        voteRepository.save(mapToVote(voteDto,post));
        postRepository.save(post);

    }

    private Vote mapToVote(VoteDto voteDto , Post post) {
        return  Vote.builder().voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}

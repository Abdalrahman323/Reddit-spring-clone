package com.springredditclone.redditclone.service;

import com.springredditclone.redditclone.dao.entities.Comment;
import com.springredditclone.redditclone.dao.entities.NotificationEmail;
import com.springredditclone.redditclone.dao.entities.Post;
import com.springredditclone.redditclone.dao.entities.User;
import com.springredditclone.redditclone.dao.repository.CommentRepository;
import com.springredditclone.redditclone.dao.repository.PostRepository;
import com.springredditclone.redditclone.dao.repository.UserRepository;
import com.springredditclone.redditclone.dto.CommentsDto;
import com.springredditclone.redditclone.exceptions.PostNotFoundException;
import com.springredditclone.redditclone.mapper.CommentMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class CommentsService {

    private static final String POST_URL = "http://localhost:8080/api/posts/";
    private  final PostRepository postRepository;
    private  final UserRepository userRepository;
    private  final CommentRepository commentRepository;
    private  final AuthService authService;
    private final CommentMapper commentMapper;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void save(CommentsDto commentsDto){

        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(()-> new PostNotFoundException(commentsDto.getPostId().toString()));

        Comment comment = commentMapper.mapToComment(commentsDto, post ,authService.getCurrentUser());

         commentRepository.save(comment);

         String message = mailContentBuilder.build(comment.getUser().getUsername()+" posted a comment on your post."
                 + POST_URL);
         sendCommentNotification(message,post.getUser(),comment.getUser());
    }

    private  void sendCommentNotification(String message , User recipient ,User commenter){
        mailService.sendMail(new NotificationEmail(
                commenter.getUsername() +" Commented on your post",
                recipient.getEmail(),
                message)
        );
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {

        List<Comment> comments = commentRepository.findAllByPost_PostId(postId)
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));

       return comments.stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());

    }

    public List<CommentsDto> getAllCommentsForUser(String userName) {
        List<Comment> comments = commentRepository.findAllByUser_Username(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));

        return comments.stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }
}

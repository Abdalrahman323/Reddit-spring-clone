package com.springredditclone.redditclone.mapper;

import com.springredditclone.redditclone.dao.entities.Post;
import com.springredditclone.redditclone.dao.entities.Subreddit;
import com.springredditclone.redditclone.dao.entities.User;
import com.springredditclone.redditclone.dao.repository.CommentRepository;
import com.springredditclone.redditclone.dao.repository.VoteRepository;
import com.springredditclone.redditclone.dto.PostRequest;
import com.springredditclone.redditclone.dto.PostResponse;
import com.springredditclone.redditclone.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;

    //Note
    //When we have methods inside the mapper with multiple input arguments,
    // we have to explicitly mention the mappings even though the source and target have the same field names.
    @Mapping(target = "createdDate" ,expression = "java(java.time.Instant.now() )")
    @Mapping(target = "subreddit" ,source = "subreddit")
    @Mapping(target = "user" ,source = "user")
    @Mapping(target = "description" ,source = "postRequest.description")
    @Mapping(target = "voteCount",constant = "0")
    public  abstract Post mapDtoToPost(PostRequest postRequest , Subreddit subreddit , User user);

    @Mapping(target = "id", source = "postId")
//    @Mapping(target = "postName",source = "postName")
//    @Mapping(target = "description",source = "description")
//    @Mapping(target = "url",source = "url")
    @Mapping(target = "subredditName",source = "subreddit.name")
    @Mapping(target = "userName" , source = "user.username")
    @Mapping(target = "commentCount" ,expression = "java(commentCount(post))")
    @Mapping(target = "duration" ,expression = "java(getDuration(post))")
    public abstract PostResponse mapToDto (Post post);

    Long commentCount(Post post){
        return commentRepository.countByPost(post);
    }

    String getDuration(Post post){
        return new PrettyTime().format((post.getCreatedDate()));
    }
}

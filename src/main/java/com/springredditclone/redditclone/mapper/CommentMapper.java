package com.springredditclone.redditclone.mapper;

import com.springredditclone.redditclone.dao.entities.Comment;
import com.springredditclone.redditclone.dao.entities.Post;
import com.springredditclone.redditclone.dao.entities.User;
import com.springredditclone.redditclone.dto.CommentsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target ="id" ,ignore = true )
    @Mapping(target = "text" , source = "commentsDto.text")
    @Mapping(target = "createdDate",expression = "java(java.time.Instant.now() )")
    @Mapping(target = "post",source = "post")
    @Mapping(target = "user" ,source = "user")
    Comment mapToComment(CommentsDto commentsDto , Post post, User user);

    @Mapping(target ="postId" , expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName",expression = "java(comment.getUser().getUsername() )")
    CommentsDto mapToDto(Comment comment);
}

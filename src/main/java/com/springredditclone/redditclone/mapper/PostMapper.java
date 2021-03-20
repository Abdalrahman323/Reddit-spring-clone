package com.springredditclone.redditclone.mapper;

import com.springredditclone.redditclone.dao.entities.Post;
import com.springredditclone.redditclone.dao.entities.Subreddit;
import com.springredditclone.redditclone.dao.entities.User;
import com.springredditclone.redditclone.dto.PostRequest;
import com.springredditclone.redditclone.dto.PostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    //Note
    //When we have methods inside the mapper with multiple input arguments,
    // we have to explicitly mention the mappings even though the source and target have the same field names.
    @Mapping(target = "createdDate" ,expression = "java(java.time.Instant.now() )")
    @Mapping(target = "subreddit" ,source = "subreddit")
    @Mapping(target = "user" ,source = "user")
    @Mapping(target = "description" ,source = "postRequest.description")
    Post mapDtoToPost(PostRequest postRequest , Subreddit subreddit , User user);

    @Mapping(target = "id", source = "postId")
//    @Mapping(target = "postName",source = "postName")
//    @Mapping(target = "description",source = "description")
//    @Mapping(target = "url",source = "url")
    @Mapping(target = "subredditName",source = "subreddit.name")
    @Mapping(target = "userName" , source = "user.username")
    PostResponse mapToDto (Post post);
}

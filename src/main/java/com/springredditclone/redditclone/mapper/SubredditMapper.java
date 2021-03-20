package com.springredditclone.redditclone.mapper;

import com.springredditclone.redditclone.dao.entities.Post;
import com.springredditclone.redditclone.dao.entities.Subreddit;
import com.springredditclone.redditclone.dto.SubredditDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target = "numberOfPosts",expression = "java(mapPosts(subreddit.getPosts() ) )")
    SubredditDTO mapSubredditToDto(Subreddit subreddit);

    default Integer mapPosts(List<Post> numberOfPosts){return  numberOfPosts.size();}

    @InheritInverseConfiguration
    @Mapping(target = "posts" ,ignore = true)
    Subreddit mapDtoToSubreddit(SubredditDTO subredditDTO);
}

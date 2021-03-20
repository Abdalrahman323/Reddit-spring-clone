package com.springredditclone.redditclone.dao.repository;

import com.springredditclone.redditclone.dao.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {


    Optional<List<Post>> findAllBySubreddit_Id(Long id);

    Optional<List<Post>> findAllByUser_Username(String username);
}

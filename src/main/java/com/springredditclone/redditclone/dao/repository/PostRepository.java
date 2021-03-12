package com.springredditclone.redditclone.dao.repository;

import com.springredditclone.redditclone.dao.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
}

package com.springredditclone.redditclone.dao.repository;

import com.springredditclone.redditclone.dao.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
}

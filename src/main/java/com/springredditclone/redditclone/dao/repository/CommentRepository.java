package com.springredditclone.redditclone.dao.repository;

import com.springredditclone.redditclone.dao.entities.Comment;
import com.springredditclone.redditclone.dao.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {


    Optional<List<Comment>> findAllByPost_PostId(Long postId);

    Optional<List<Comment>> findAllByUser_Username(String userName);

    long countByPost(Post post);

}

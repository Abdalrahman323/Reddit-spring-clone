package com.springredditclone.redditclone.dao.repository;

import com.springredditclone.redditclone.dao.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {


    Optional<Vote> findTopByPost_PostIdAndUser_UserIdOrderByVoteIdDesc(Long postId,Long userId);
}

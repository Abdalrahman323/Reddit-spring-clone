package com.springredditclone.redditclone.dao.repository;

import com.springredditclone.redditclone.dao.entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepository extends JpaRepository<VerificationToken,Long> {
}

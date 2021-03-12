package com.springredditclone.redditclone.dao.repository;

import com.springredditclone.redditclone.dao.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}

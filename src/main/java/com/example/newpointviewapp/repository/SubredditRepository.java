package com.example.newpointviewapp.repository;

import com.example.newpointviewapp.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubredditRepository extends JpaRepository<Subreddit,Long> {
}

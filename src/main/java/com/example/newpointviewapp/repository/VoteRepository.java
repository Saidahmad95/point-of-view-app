package com.example.newpointviewapp.repository;


import com.example.newpointviewapp.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VoteRepository extends JpaRepository<Vote,Long> {
}

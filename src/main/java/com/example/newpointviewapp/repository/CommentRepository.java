package com.example.newpointviewapp.repository;


import com.example.newpointviewapp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}

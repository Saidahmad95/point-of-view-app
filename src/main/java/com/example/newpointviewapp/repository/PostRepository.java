package com.example.newpointviewapp.repository;


import com.example.newpointviewapp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}

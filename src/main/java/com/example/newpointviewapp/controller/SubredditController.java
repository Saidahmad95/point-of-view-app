package com.example.newpointviewapp.controller;

import com.example.newpointviewapp.dto.SubredditDto;
import com.example.newpointviewapp.service.SubredditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/subreddit")
public class SubredditController {

    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto) {
        SubredditDto savedSubredditDto = subredditService.save(subredditDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedSubredditDto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<SubredditDto>> getAllSubreddits() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subredditService.getAll());
    }

}

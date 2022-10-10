package com.shohag.Backend.controllers;

import com.shohag.Backend.dtos.CommentDto;
import com.shohag.Backend.payloads.ApiResponse;
import com.shohag.Backend.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Long postId) {
        CommentDto comment = this.commentService.createComment(commentDto, postId);
        return new ResponseEntity<CommentDto>(comment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long commentId) {
        this.commentService.deleteComment(commentId);
        ApiResponse apiResponse = new ApiResponse("Comment Deleted Successfully", true);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }
}

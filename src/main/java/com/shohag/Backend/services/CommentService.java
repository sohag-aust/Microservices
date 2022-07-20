package com.shohag.Backend.services;

import com.shohag.Backend.dtos.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Long postId);
    void deleteComment(Long commentId);
}

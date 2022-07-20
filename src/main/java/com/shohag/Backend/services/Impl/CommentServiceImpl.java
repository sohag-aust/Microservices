package com.shohag.Backend.services.Impl;

import com.shohag.Backend.dtos.CommentDto;
import com.shohag.Backend.entities.Comment;
import com.shohag.Backend.entities.Post;
import com.shohag.Backend.exceptions.ResourceNotFoundException;
import com.shohag.Backend.repositories.CommentRepo;
import com.shohag.Backend.repositories.PostRepo;
import com.shohag.Backend.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final PostRepo postRepo;
    private final CommentRepo commentRepo;

    private final ModelMapper modelMapper;

    public CommentServiceImpl(PostRepo postRepo, CommentRepo commentRepo, ModelMapper modelMapper) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, Long postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));

        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);

        Comment createdComment = this.commentRepo.save(comment);
        return this.modelMapper.map(createdComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "CommentId", commentId));
        this.commentRepo.delete(comment);
    }
}

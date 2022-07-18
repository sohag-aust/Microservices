package com.shohag.Backend.services;

import com.shohag.Backend.dtos.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto, Long userId, Long categoryId);
    PostDto updatePost(PostDto postDto, Long postId);
    void deletePost(Long postId);
    List<PostDto> getPosts();
    PostDto getPostById(Long postId);
    List<PostDto> getPostsByCategory(Long categoryId);
    List<PostDto> getPostsByUser(Long userId);
    List<PostDto> searchPosts(String keyword);
}

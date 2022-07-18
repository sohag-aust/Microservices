package com.shohag.Backend.services;

import com.shohag.Backend.dtos.PostDto;
import com.shohag.Backend.entities.Post;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto, Long userId, Long categoryId);
    PostDto updatePost(PostDto postDto, Long postId);
    void deletePost(Long postId);
    List<Post> getPosts();
    Post getPostById(Long postId);
    List<Post> getPostsByCategory(Long categoryId);
    List<Post> getPostsByUser(Long userId);
    List<Post> searchPosts(String keyword);
}

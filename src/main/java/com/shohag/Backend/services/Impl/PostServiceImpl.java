package com.shohag.Backend.services.Impl;

import com.shohag.Backend.dtos.PostDto;
import com.shohag.Backend.entities.Category;
import com.shohag.Backend.entities.Post;
import com.shohag.Backend.entities.User;
import com.shohag.Backend.exceptions.ResourceNotFoundException;
import com.shohag.Backend.repositories.CategoryRepo;
import com.shohag.Backend.repositories.PostRepo;
import com.shohag.Backend.repositories.UserRepo;
import com.shohag.Backend.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final ModelMapper modelMapper;
    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;

    public PostServiceImpl(PostRepo postRepo, ModelMapper modelMapper, UserRepo userRepo, CategoryRepo categoryRepo) {
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public PostDto createPost(PostDto postDto, Long userId, Long categoryId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post savedPost = this.postRepo.save(post);
        return this.modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long postId) {
        return null;
    }

    @Override
    public void deletePost(Long postId) {

    }

    @Override
    public List<Post> getPosts() {
        return null;
    }

    @Override
    public Post getPostById(Long postId) {
        return null;
    }

    @Override
    public List<Post> getPostsByCategory(Long categoryId) {
        return null;
    }

    @Override
    public List<Post> getPostsByUser(Long userId) {
        return null;
    }

    @Override
    public List<Post> searchPosts(String keyword) {
        return null;
    }
}

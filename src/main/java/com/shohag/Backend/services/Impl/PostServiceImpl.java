package com.shohag.Backend.services.Impl;

import com.shohag.Backend.dtos.PostDto;
import com.shohag.Backend.entities.Category;
import com.shohag.Backend.entities.Post;
import com.shohag.Backend.entities.User;
import com.shohag.Backend.exceptions.ResourceNotFoundException;
import com.shohag.Backend.payloads.PostResponse;
import com.shohag.Backend.repositories.CategoryRepo;
import com.shohag.Backend.repositories.PostRepo;
import com.shohag.Backend.repositories.UserRepo;
import com.shohag.Backend.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        Post updatedPost = this.postRepo.save(post);
        return this.modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Long postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getPosts(Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {

        // for getting all posts from DB
        // List<Post> posts = this.postRepo.findAll();

        // for pagination
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        if(sortOrder.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        }

        Page<Post> pagedPosts = this.postRepo.findAll(pageable);
        List<Post> posts = pagedPosts.getContent();

        List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postDtos);
        postResponse.setPageNo(pagedPosts.getNumber());
        postResponse.setPageSize(pagedPosts.getSize());
        postResponse.setTotalElements(pagedPosts.getTotalElements());
        postResponse.setTotalPages(pagedPosts.getTotalPages());
        postResponse.setLastPage(pagedPosts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Long postId) {
        //Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
        Post post = this.postRepo.findByPostId(postId);
        return PostDto.entityToDto(post);
    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
        List<Post> posts = this.postRepo.findByCategory(category);
        List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByUser(Long userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));
        List<Post> posts = this.postRepo.findByUser(user);
        List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = this.postRepo.findByTitleContaining(keyword);
        List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
}

package com.shohag.Backend.controllers;

import com.shohag.Backend.constants.AppConstants;
import com.shohag.Backend.constants.Paging;
import com.shohag.Backend.dtos.PostDto;
import com.shohag.Backend.dtos.UserDto;
import com.shohag.Backend.payloads.ApiResponse;
import com.shohag.Backend.payloads.PostResponse;
import com.shohag.Backend.services.PostService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Long userId, @PathVariable Long categoryId) {
        PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Long userId) {
        List<PostDto> posts = this.postService.getPostsByUser(userId);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Long categoryId) {
        List<PostDto> posts = this.postService.getPostsByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getPosts(@RequestParam(value = "pageNo", defaultValue = AppConstants.PAGE_NO, required = false) Integer pageNo,
                                                 @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                 @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                 @RequestParam(value = "sortOrder", defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder) {

//        List<PostDto> posts = this.postService.getPosts(pageNo, pageSize);
//        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

        // getting enum string value
        String defaultPageNo = Paging.DEFAULT_PAGE_NO.getValue();
        String defaultPageSize = Paging.DEFAULT_PAGE_SIZE.getValue();

        System.out.println("== Page No: " + defaultPageNo);
        System.out.println("== Page Size: " + defaultPageSize);

        PostResponse postResponse = this.postService.getPosts(pageNo, pageSize, sortBy, sortOrder);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId) {
        PostDto post = this.postService.getPostById(postId);
        return new ResponseEntity<PostDto>(post, HttpStatus.OK);
    }

    @PutMapping("/post/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Long postId) {
        PostDto updatedPost = this.postService.updatePost(postDto, postId);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Long postId) {
        this.postService.deletePost(postId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Successfully", true), HttpStatus.OK);
    }

    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPosts(@PathVariable String keywords) {
        List<PostDto> posts = this.postService.searchPosts(keywords);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
    }
}

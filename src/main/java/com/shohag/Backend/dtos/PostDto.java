package com.shohag.Backend.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shohag.Backend.entities.Comment;
import com.shohag.Backend.entities.Post;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class PostDto {

    @JsonProperty("id")
    private Long postId;
    private String title;
    private String content;

    @JsonProperty("image_name")
    private String imageName = "default.png";

    @JsonProperty("added_date")
    private Date addedDate;

    private CategoryDto category;
    private UserDto user;
//    private Set<CommentDto> comments = new HashSet<>();

    public static PostDto entityToDto(Post post) {
//        System.out.println("== Post for converting to DTO : " + post.toString());
        return new PostDto()
                .setPostId(post.getPostId())
                .setTitle(post.getTitle())
                .setContent(post.getContent())
                .setImageName(post.getImageName())
                .setAddedDate(post.getAddedDate())
                .setCategory(CategoryDto.entityToDto(post.getCategory()))
                .setUser(UserDto.entityToDto(post.getUser()));

//                .setComments(post.getComments()
//                        .stream()
//                        .map(CommentDto::entityToDto)
//                        .collect(Collectors.toSet())
//                );
    }
}

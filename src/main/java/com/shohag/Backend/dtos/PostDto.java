package com.shohag.Backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class PostDto {

    private String title;
    private String content;

    @JsonProperty("image_name")
    private String imageName = "default.png";

    @JsonProperty("added_date")
    private Date addedDate;

    private CategoryDto category;
    private UserDto user;
}

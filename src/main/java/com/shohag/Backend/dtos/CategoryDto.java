package com.shohag.Backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CategoryDto {

    private Long categoryId;

    @JsonProperty("title")
    @NotBlank
    @Size(min = 4)
    private String categoryTitle;

    @JsonProperty("description")
    @NotBlank
    @Size(min = 10)
    private String categoryDescription;
}

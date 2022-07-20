package com.shohag.Backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shohag.Backend.entities.Category;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
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

    public static CategoryDto entityToDto(Category category) {
        return new CategoryDto()
                .setCategoryId(category.getCategoryId())
                .setCategoryTitle(category.getCategoryTitle())
                .setCategoryDescription(category.getCategoryDescription());
    }


}

package com.shohag.Backend.payloads;

import com.shohag.Backend.dtos.PostDto;
import lombok.Data;

import java.util.List;

@Data
public class PostResponse {
    private List<PostDto> content;
    private int pageNo;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private boolean isLastPage;
}

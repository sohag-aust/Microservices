package com.shohag.Backend.dtos;

import com.shohag.Backend.entities.Comment;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CommentDto {

    private Long id;
    private String content;

    public static CommentDto entityToDto(Comment comment) {
        return new CommentDto()
                .setId(comment.getCommentId())
                .setContent(comment.getContent());
    }
}

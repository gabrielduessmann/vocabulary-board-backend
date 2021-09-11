package com.vocabulary.board.comment;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO implements Serializable {
    private UUID id;
    private String comment;


    public static CommentDTO toDto(Comment comment) {
        return new CommentDTO(comment.getId(), comment.getComment());
    }
}

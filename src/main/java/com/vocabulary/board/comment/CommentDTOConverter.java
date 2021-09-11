package com.vocabulary.board.comment;

import dto.CommentDTO;

public class CommentDTOConverter {
    public static CommentDTO toDto(Comment comment) {
        return new CommentDTO(comment.getId(), comment.getComment());
    }
}
package com.vocabulary.board.builders;

import com.vocabulary.board.comment.Comment;
import com.vocabulary.board.vocabulary.Vocabulary;
import java.util.UUID;

public class CommentBuilder {

    private Comment entity;

    private CommentBuilder(Comment comment) {
        comment.setId(UUID.randomUUID());
        entity = comment;
    }

    public static CommentBuilder oneComment() {
        return new CommentBuilder(new Comment());
    }

    public CommentBuilder withId(UUID id) {
        entity.setId(id);
        return this;
    }

    public CommentBuilder withVocabulary(Vocabulary vocabulary) {
         entity.setVocabulary(vocabulary);
         return this;
    }

    public Comment build() {
        return entity;
    }

}

package com.vocabulary.board.builders;

import com.vocabulary.board.comment.Comment;
import com.vocabulary.board.vocabulary.Vocabulary;
import java.util.Date;
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

    public CommentBuilder withVocabulary(Vocabulary vocabulary) {
         entity.setVocabulary(vocabulary);
         return this;
    }

    public CommentBuilder withComment(String comment) {
        entity.setComment(comment);
        return this;
    }

    public CommentBuilder withCommentedDate(Date date) {
        entity.setCommentedDate(date);
        return this;
    }

    public Comment build() {
        return entity;
    }

}

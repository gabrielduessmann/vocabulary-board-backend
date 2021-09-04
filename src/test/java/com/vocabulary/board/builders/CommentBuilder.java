package com.vocabulary.board.builders;

import com.vocabulary.board.comment.Comment;
import com.vocabulary.board.vocabulary.Vocabulary;

public class CommentBuilder {

    private Comment entity;

    private CommentBuilder(Comment comment) {
        entity = comment;
    }

    public static CommentBuilder oneComment() {
        return new CommentBuilder(new Comment());
    }

    public CommentBuilder withVocabulary(Vocabulary vocabulary) {
         entity.setVocabulary(vocabulary);
         return this;
    }

    public Comment build() {
        return entity;
    }

}

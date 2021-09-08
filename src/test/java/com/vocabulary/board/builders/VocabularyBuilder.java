package com.vocabulary.board.builders;

import com.vocabulary.board.column.Column;
import com.vocabulary.board.comment.Comment;
import com.vocabulary.board.vocabulary.Vocabulary;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class VocabularyBuilder {
    private Vocabulary entity;

    private VocabularyBuilder(Vocabulary vocabulary) {
        vocabulary.setId(UUID.randomUUID());
        entity = vocabulary;
    }

    public static VocabularyBuilder oneVocabulary() {
        return new VocabularyBuilder(new Vocabulary());
    }

    public VocabularyBuilder withId(UUID id) {
        entity.setId(id);
        return this;
    }

    public VocabularyBuilder withColumn(Column column) {
        entity.setColumn(column);
        return this;
    }

    public VocabularyBuilder withWord(String word) {
        entity.setWord(word);
        return this;
    }

    public VocabularyBuilder withDescription(String description) {
        entity.setDescription(description);
        return this;
    }

    public VocabularyBuilder withDate(Date date) {
        entity.setCreationDate(date);
        return this;
    }

    public VocabularyBuilder withComments(List<Comment> comments) {
        entity.setComment(comments);
        return this;
    }

    public Vocabulary build() {
        return entity;
    }

}

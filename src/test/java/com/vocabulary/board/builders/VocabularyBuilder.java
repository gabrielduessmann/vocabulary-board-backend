package com.vocabulary.board.builders;

import com.vocabulary.board.column.Column;
import com.vocabulary.board.vocabulary.Vocabulary;
import java.util.Date;

public class VocabularyBuilder {
    private Vocabulary entity;

    private VocabularyBuilder(Vocabulary vocabulary) {
        entity = vocabulary;
    }

    public static VocabularyBuilder oneVocabulary() {
        return new VocabularyBuilder(new Vocabulary());
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

    public Vocabulary build() {
        return entity;
    }

}

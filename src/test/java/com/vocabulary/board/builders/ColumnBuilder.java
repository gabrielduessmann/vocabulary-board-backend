package com.vocabulary.board.builders;

import com.vocabulary.board.column.Column;
import com.vocabulary.board.column.enums.StatusEnum;
import com.vocabulary.board.vocabulary.Vocabulary;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ColumnBuilder {
    private Column entity;

    private ColumnBuilder(Column column) {
        column.setId(UUID.randomUUID());
        entity = column;
    }

    public static ColumnBuilder oneColumn() {
        return new ColumnBuilder(new Column());
    }

    public ColumnBuilder withId(UUID id) {
        entity.setId(id);
        return this;
    }

    public ColumnBuilder withTitle(String title) {
        entity.setTitle(title);
        return this;
    }

    public ColumnBuilder withNextUpdate(Date nextUpdate) {
        entity.setNextUpdate(nextUpdate);
        return this;
    }

    public ColumnBuilder withStatus(StatusEnum status) {
        entity.setStatus(status);
        return this;
    }

    public ColumnBuilder withSprintOrder(Integer sprintOrder) {
        entity.setSprintOrder(sprintOrder);
        return this;
    }

    public ColumnBuilder withVocabularies(List<Vocabulary> vocabularies) {
        entity.setVocabularies(vocabularies);
        return this;
    }

    public Column build() {
        return entity;
    }

}

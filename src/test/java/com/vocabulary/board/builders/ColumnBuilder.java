package com.vocabulary.board.builders;

import com.vocabulary.board.column.Column;
import com.vocabulary.board.column.enums.StatusEnum;
import java.util.Date;

public class ColumnBuilder {
    private Column entity;

    private ColumnBuilder() {}

    public static ColumnBuilder oneColumn() {
        ColumnBuilder builder = new ColumnBuilder();

        Column entity = new Column();

        builder.entity = entity;
        return builder;
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

    public Column build() {
        return entity;
    }

}

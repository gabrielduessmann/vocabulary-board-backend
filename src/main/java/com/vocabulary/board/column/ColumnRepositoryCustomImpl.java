package com.vocabulary.board.column;

import com.mysema.query.jpa.impl.JPAQuery;
import com.vocabulary.board.column.enums.StatusEnum;
import com.vocabulary.board.column.qdsl.QColumn;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ColumnRepositoryCustomImpl implements ColumnRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Integer getMaxSprintOrder() {
        JPAQuery query = new JPAQuery(entityManager);
        QColumn qColumn = new QColumn("column");
        return query
                .from(qColumn)
                .where(qColumn.status.eq(StatusEnum.IN_PROGRESS))
                .orderBy(qColumn.sprintOrder.desc())
                .singleResult(qColumn.sprintOrder);
    }
}

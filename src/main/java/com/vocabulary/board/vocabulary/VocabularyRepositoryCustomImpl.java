package com.vocabulary.board.vocabulary;

import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import com.vocabulary.board.column.Column;
import com.vocabulary.board.column.enums.StatusEnum;
import com.vocabulary.board.column.qdsl.QColumn;
import com.vocabulary.board.vocabulary.qdsl.QVocabulary;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;


public class VocabularyRepositoryCustomImpl implements VocabularyRepositoryCustom{

    @PersistenceContext()
    private EntityManager entityManager;

    @Override
    public List<Vocabulary> saveNewVocabulary() {

        JPAQuery query = new JPAQuery(entityManager);

        QColumn qColumn = new QColumn("column");
        Column column = query
                .from(qColumn)
                .where(qColumn.status.eq(StatusEnum.POOL))
                .uniqueResult(qColumn);

        QVocabulary qVocabulary = new QVocabulary("vocabulary");

        return new ArrayList<Vocabulary>();
    }


}

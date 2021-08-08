package com.vocabulary.board.vocabulary;


import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.vocabulary.board.column.enums.StatusEnum;
import com.vocabulary.board.column.qdsl.QColumn;
import com.vocabulary.board.vocabulary.qdsl.QVocabulary;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.UUID;

public class VocabularyRepositoryCustomImpl implements VocabularyRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Vocabulary moveToColumn(UUID vocabularyId, StatusEnum status) {

        QColumn qColumn = new QColumn("column");
        JPAQuery query = new JPAQuery(entityManager);
        UUID columnId = query
                .from(qColumn)
                .where(qColumn.status.eq(status))
                .uniqueResult(qColumn).getId();

        QVocabulary qVocabulary = new QVocabulary("vocabulary");
        JPAUpdateClause queryUpdate = new JPAUpdateClause(entityManager, qVocabulary);
        queryUpdate
                .set(qVocabulary.column().id, columnId)
                .where(qVocabulary.id.eq(vocabularyId))
                .execute();

        return query
                .from(qVocabulary)
                .where(qVocabulary.id.eq(vocabularyId))
                .uniqueResult(qVocabulary);
    }
}

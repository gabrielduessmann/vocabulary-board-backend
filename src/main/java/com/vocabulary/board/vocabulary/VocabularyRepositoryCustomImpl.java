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
    public Vocabulary moveColumn(UUID vocabularyId, StatusEnum status,  Integer sprintOrder) {

        QColumn qColumn = new QColumn("column");
        JPAQuery query = new JPAQuery(entityManager);
        query.from(qColumn);

        if (sprintOrder != null) {
            query.where(qColumn.sprintOrder.eq(sprintOrder));
        } else if (status != null) {
            query.where(qColumn.status.eq(status));
        }
        UUID columnId = query.uniqueResult(qColumn).getId();

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

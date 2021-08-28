package com.vocabulary.board.comment;

import com.mysema.query.jpa.impl.JPAQuery;
import com.vocabulary.board.comment.qdsl.QComment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

public class CommentRepositoryCustomImpl implements CommentRepositoryCustom{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Comment> findAllByVocabularyId(UUID vocabularyId) {
        QComment qComment = new QComment("comment");
        JPAQuery query = new JPAQuery(em);
        return query
                .from(qComment)
                .where(qComment.vocabulary().id.eq(vocabularyId))
                .orderBy(qComment.commentedDate.desc())
                .list(qComment);
    }
}

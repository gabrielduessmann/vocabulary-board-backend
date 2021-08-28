package com.vocabulary.board.comment;

import java.util.List;
import java.util.UUID;

public interface CommentRepositoryCustom {
    List<Comment> findAllByVocabularyId(UUID vocabularyId);
}

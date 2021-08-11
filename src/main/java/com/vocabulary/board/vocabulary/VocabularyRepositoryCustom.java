package com.vocabulary.board.vocabulary;

import com.vocabulary.board.column.enums.StatusEnum;

import java.util.UUID;

public interface VocabularyRepositoryCustom {

    Vocabulary moveColumn(UUID vocabularyId, StatusEnum status, Integer sprintOrder);
}

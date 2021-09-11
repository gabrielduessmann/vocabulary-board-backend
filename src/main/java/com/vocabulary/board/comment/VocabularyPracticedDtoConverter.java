package com.vocabulary.board.comment;

import com.vocabulary.board.vocabulary.Vocabulary;
import dto.VocabularyPracticedDto;

public class VocabularyPracticedDtoConverter {
    public static VocabularyPracticedDto toDto(Vocabulary entity) {
        return new VocabularyPracticedDto(entity.getId());
    }
}
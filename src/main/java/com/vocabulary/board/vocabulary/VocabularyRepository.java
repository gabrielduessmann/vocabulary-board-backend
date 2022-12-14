package com.vocabulary.board.vocabulary;

import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface VocabularyRepository extends CrudRepository<Vocabulary, UUID>, VocabularyRepositoryCustom {
    List<Vocabulary> findByColumnId(UUID columnId);

    List<Vocabulary> findAllByOrderByWordAsc();
}

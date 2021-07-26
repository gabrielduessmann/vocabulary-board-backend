package com.vocabulary.board.vocabulary;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface VocabularyRepository extends CrudRepository<Vocabulary, UUID> {
}

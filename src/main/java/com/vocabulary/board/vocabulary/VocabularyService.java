package com.vocabulary.board.vocabulary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VocabularyService {

    @Autowired
    private VocabularyRepository vocabRepository;

    public void addVocabulary(Vocabulary vocab) {
        vocabRepository.save(vocab);
    }

    public Vocabulary getOneVocabulary(String id) {
        return vocabRepository.findById(id).orElse(null);
    }
}

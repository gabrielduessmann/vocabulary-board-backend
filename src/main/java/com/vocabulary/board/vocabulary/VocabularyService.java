package com.vocabulary.board.vocabulary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VocabularyService {

    @Autowired
    private VocabularyRepository vocabRepository;

    public Vocabulary addVocabulary(Vocabulary vocab) {
        return vocabRepository.save(vocab);
    }

    public Vocabulary getOneVocabulary(String id) {
        return vocabRepository.findById(id).orElse(null);
    }

    public List<Vocabulary> getAllVocabularies() {
        List<Vocabulary> vocabs = new ArrayList<>();
        vocabRepository.findAll().forEach(vocabs::add);
        return vocabs;
    }
}

package com.vocabulary.board.vocabulary;

import java.util.Optional;
import java.util.UUID;
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

    public Optional<Vocabulary> getOneVocabulary(UUID id) {
        return vocabRepository.findById(id);
    }

    public List<Vocabulary> getAllVocabularies() {
        List<Vocabulary> vocabs = new ArrayList<>();
        vocabRepository.findAll().forEach(vocabs::add);
        return vocabs;
    }

    public List<Vocabulary> findVocabulariesByColumnId(UUID columnId) {
        List<Vocabulary> vocabs = new ArrayList<>();
        vocabRepository.findByColumnId(columnId).forEach(vocabs::add);
        return vocabs;
    }
}

package com.vocabulary.board.vocabulary;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VocabularyServiceTest {

    @InjectMocks
    private VocabularyService vocabularyService;

    @Mock
    private VocabularyRepository vocabularyRepository;

    @Test
    public void add() {
        Vocabulary vocabExpected = new Vocabulary("123", "beautiful", "adjective");

        Mockito.when(vocabularyRepository.save(vocabExpected)).thenReturn(vocabExpected);

        Vocabulary vocab = vocabularyService.addVocabulary(vocabExpected);
        Assertions.assertEquals(vocabExpected, vocab);
    }

    @Test
    public void getOneVocabulary() {
        Vocabulary vocabExpected = new Vocabulary("123", "beautiful", "adjective");

        Mockito.when(vocabularyRepository.findById("123")).thenReturn(java.util.Optional.of(vocabExpected));

        Vocabulary vocab = vocabularyService.getOneVocabulary("123");
        Assertions.assertEquals(vocabExpected, vocab);
    }

}

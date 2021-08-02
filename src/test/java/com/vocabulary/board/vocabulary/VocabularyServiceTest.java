package com.vocabulary.board.vocabulary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VocabularyServiceTest {

    @InjectMocks
    private VocabularyService vocabularyService;

    @Mock
    private VocabularyRepository vocabularyRepository;

    private static final Vocabulary VOCABULARY = new Vocabulary(UUID.randomUUID(), "beautiful", "adjective", new Date());


    @Test
    void add_success() {
        when(vocabularyRepository.save(VOCABULARY)).thenReturn(VOCABULARY);

        Vocabulary vocab = vocabularyService.addVocabulary(VOCABULARY);

        assertEquals(VOCABULARY, vocab);
    }

    @Test
    void getOneVocabulary_success() {
        var id = UUID.randomUUID();
        when(vocabularyRepository.findById(id)).thenReturn(Optional.of(VOCABULARY));

        Optional<Vocabulary> vocab = vocabularyService.getOneVocabulary(id);

        assertTrue(vocab.isPresent());
        assertEquals(VOCABULARY, vocab.get());
    }

}

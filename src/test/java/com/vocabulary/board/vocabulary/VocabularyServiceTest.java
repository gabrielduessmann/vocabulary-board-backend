package com.vocabulary.board.vocabulary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
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

    @Test
    void add_success() {
        Vocabulary vocabExpected = new Vocabulary(UUID.randomUUID(), "beautiful", "adjective");
        when(vocabularyRepository.save(vocabExpected)).thenReturn(vocabExpected);

        Vocabulary vocab = vocabularyService.addVocabulary(vocabExpected);

        assertEquals(vocabExpected, vocab);
    }

    @Test
    void getOneVocabulary_sucess() {
        var id = UUID.randomUUID();
        Vocabulary vocabExpected = new Vocabulary(id, "beautiful", "adjective");
        when(vocabularyRepository.findById(id)).thenReturn(Optional.of(vocabExpected));

        Optional<Vocabulary> vocab = vocabularyService.getOneVocabulary(id);

        assertTrue(vocab.isPresent());
        assertEquals(vocabExpected, vocab.get());
    }

}

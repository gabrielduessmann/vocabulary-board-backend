package com.vocabulary.board.vocabulary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.security.InvalidParameterException;
import java.util.*;
import com.vocabulary.board.builders.ColumnBuilder;
import com.vocabulary.board.builders.CommentBuilder;
import com.vocabulary.board.builders.VocabularyBuilder;
import com.vocabulary.board.column.Column;
import com.vocabulary.board.column.ColumnRepository;
import com.vocabulary.board.column.enums.StatusEnum;
import com.vocabulary.board.comment.Comment;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

@ExtendWith(MockitoExtension.class)
public class VocabularyServiceTest {

    @Spy
    @InjectMocks
    private VocabularyService vocabularyService;

    @Mock
    private VocabularyRepository vocabularyRepository;

    @Mock
    private ColumnRepository columnRepository;

    private final Column COLUMN = ColumnBuilder.oneColumn().build();
    private final Comment COMMENT = CommentBuilder.oneComment().build();
    private final Vocabulary VOCABULARY = new Vocabulary(UUID.randomUUID(), "beautiful", "adjective", new Date(), COLUMN, List.of(COMMENT));

    @Test
    void addVocabulary() {
        when(vocabularyRepository.save(VOCABULARY)).thenReturn(VOCABULARY);

        Vocabulary vocab = vocabularyService.addVocabulary(VOCABULARY);

        assertEquals(VOCABULARY, vocab);
    }

    @Test
    void updateVocabulary() {
        Vocabulary newVocabulary = VocabularyBuilder.oneVocabulary().withWord("apple").withDescription("a red fruit").build();
        when(vocabularyRepository.findById(newVocabulary.getId())).thenReturn(Optional.of(VOCABULARY));
        when(vocabularyRepository.save(VOCABULARY)).thenReturn(VOCABULARY);

        Vocabulary vocab = vocabularyService.updateVocabulary(newVocabulary);

        assertEquals(VOCABULARY, vocab);
        assertEquals(newVocabulary.getWord(), vocab.getWord());
        assertEquals(newVocabulary.getDescription(), vocab.getDescription());
    }

    @Test
    void updateVocabulary_exception() {
        Vocabulary newVocabulary = VocabularyBuilder.oneVocabulary().withWord("apple").withDescription("a red fruit").build();
        when(vocabularyRepository.findById(newVocabulary.getId())).thenThrow(InvalidParameterException.class);

        assertThrows(InvalidParameterException.class, () -> vocabularyService.updateVocabulary(newVocabulary));
    }

    @Test
    void saveNewVocabulary() {
        Column column = ColumnBuilder.oneColumn().withStatus(StatusEnum.POOL).build();
        when(columnRepository.findByStatus(StatusEnum.POOL)).thenReturn(column);
        when(vocabularyRepository.save(VOCABULARY)).thenReturn(VOCABULARY);

        Vocabulary vocab = vocabularyService.saveNewVocabulary(VOCABULARY);

        assertEquals(VOCABULARY, vocab);
        assertEquals(column.getId(), vocab.getColumn().getId());
    }

    @Test
    void saveNewVocabulary_exception_noColumnInPool() {
        when(columnRepository.findByStatus(StatusEnum.POOL)).thenReturn(null);
        String expectedMessage = "There is no column with POOL status";

        Exception exception = assertThrows(ServiceException.class, () -> vocabularyService.saveNewVocabulary(VOCABULARY));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void saveNewVocabulary_exception_multipleColumnsInPool() {
        when(columnRepository.findByStatus(StatusEnum.POOL)).thenThrow(IncorrectResultSizeDataAccessException.class);
        String expectedMessage = "There is more than one column with status POOL";

        Exception exception = assertThrows(ServiceException.class, () -> vocabularyService.saveNewVocabulary(VOCABULARY));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getOneVocabulary() {
        var id = UUID.randomUUID();
        when(vocabularyRepository.findById(id)).thenReturn(Optional.of(VOCABULARY));

        Optional<Vocabulary> vocab = vocabularyService.getOneVocabulary(id);

        assertThat(vocab).isPresent().get().isEqualTo(VOCABULARY);
    }

    @Test
    void getOneVocabulary_empty() {
        UUID id = UUID.randomUUID();
        when(vocabularyRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Vocabulary> vocab = vocabularyService.getOneVocabulary(id);

        assertThat(vocab).isNotPresent();
    }

    @Test
    void getAllVocabularies() {
        List<Vocabulary> vocabulariesExpected = new ArrayList<>();
        vocabulariesExpected.add(VOCABULARY);
        vocabulariesExpected.add(VocabularyBuilder.oneVocabulary().build());
        vocabulariesExpected.add(VocabularyBuilder.oneVocabulary().withWord("country").build());
        when(vocabularyRepository.findAll()).thenReturn(vocabulariesExpected);

        List<Vocabulary> vocabs = vocabularyService.getAllVocabularies();

        assertThat(vocabs).hasSize(3).isEqualTo(vocabulariesExpected);
    }

    @Test
    void findVocabulariesByColumnId() {
        List<Vocabulary> vocabulariesExpected = new ArrayList<>();
        vocabulariesExpected.add(VOCABULARY);
        vocabulariesExpected.add(VocabularyBuilder.oneVocabulary().withColumn(COLUMN).build());
        when(vocabularyRepository.findByColumnId(COLUMN.getId())).thenReturn(vocabulariesExpected);

        List<Vocabulary> vocabs = vocabularyService.findVocabulariesByColumnId(COLUMN.getId());

        assertThat(vocabs).hasSize(2).isEqualTo(vocabulariesExpected);
    }

    @Test
    void moveToNextColumn() {
        Column column = ColumnBuilder.oneColumn().withStatus(StatusEnum.BACKLOG).withSprintOrder(0).build();
        Vocabulary vocabulary = VocabularyBuilder.oneVocabulary().withColumn(column).build();
        when(vocabularyRepository.findById(vocabulary.getId())).thenReturn(Optional.of(vocabulary));
        Column columnToMove = ColumnBuilder.oneColumn().withStatus(StatusEnum.IN_PROGRESS).withSprintOrder(1).build();
        when(vocabularyService.whichColumnToMove(vocabulary.getColumn())).thenReturn(columnToMove);
        Vocabulary expectedVocabulary = VocabularyBuilder.oneVocabulary().withId(vocabulary.getId()).withColumn(columnToMove).build();
        when(vocabularyRepository.moveColumn(vocabulary.getId(), columnToMove.getStatus(), columnToMove.getSprintOrder())).thenReturn(expectedVocabulary);

        Vocabulary vocabMoved = vocabularyService.moveToNextColumn(vocabulary.getId());

        assertEquals(expectedVocabulary, vocabMoved);
    }

    @Test
    void whichColumnToMove_currentStatusInPool() {
        Column column = ColumnBuilder.oneColumn().withStatus(StatusEnum.POOL).withSprintOrder(0).build();

        Column columnTarget = vocabularyService.whichColumnToMove(column);

        assertEquals(StatusEnum.BACKLOG, columnTarget.getStatus());
        assertNull(columnTarget.getSprintOrder());
    }

    @Test
    void whichColumnToMove_currentStatusInBacklog() {
        Column column = ColumnBuilder.oneColumn().withStatus(StatusEnum.BACKLOG).withSprintOrder(0).build();

        Column columnTarget = vocabularyService.whichColumnToMove(column);

        assertNull(columnTarget.getStatus());
        assertEquals(1, columnTarget.getSprintOrder());
    }

    @Test
    void whichColumnToMove_currentStatusInProgress() {
        Column column = ColumnBuilder.oneColumn().withStatus(StatusEnum.IN_PROGRESS).withSprintOrder(3).build();
        when(columnRepository.getMaxSprintOrder()).thenReturn(11);

        Column columnTarget = vocabularyService.whichColumnToMove(column);

        assertNull(columnTarget.getStatus());
        assertEquals(4, columnTarget.getSprintOrder());
    }

    @Test
    void whichColumnToMove_currentStatusInPaused() {
        Column column = ColumnBuilder.oneColumn().withStatus(StatusEnum.PAUSED).withSprintOrder(4).build();

        Column columnTarget = vocabularyService.whichColumnToMove(column);

        assertNull(columnTarget.getStatus());
        assertEquals(5, columnTarget.getSprintOrder());
    }

    @Test
    void whichColumnToMove_currentStatusInProgressAndLastSprint() {
        Column column = ColumnBuilder.oneColumn().withStatus(StatusEnum.IN_PROGRESS).withSprintOrder(11).build();
        when(columnRepository.getMaxSprintOrder()).thenReturn(11);

        Column columnTarget = vocabularyService.whichColumnToMove(column);

        assertEquals(StatusEnum.DONE, columnTarget.getStatus());
        assertNull(columnTarget.getSprintOrder());
    }

}

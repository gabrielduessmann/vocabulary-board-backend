package com.vocabulary.board.column;

import com.vocabulary.board.builders.ColumnBuilder;
import com.vocabulary.board.builders.VocabularyBuilder;
import com.vocabulary.board.column.enums.StatusEnum;
import com.vocabulary.board.vocabulary.Vocabulary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ColumnServiceTest {

    @Spy
    @InjectMocks
    private ColumnService columnService;

    @Mock
    private ColumnRepository columnRepository;

    @Test
    void findById() {
        Column column = ColumnBuilder.oneColumn().build();
        when(columnRepository.findById(column.getId())).thenReturn(Optional.of(column));

        Optional<Column> columnFound = columnService.findById(column.getId());

        assertThat(columnFound).isPresent().get().isEqualTo(column);
    }

    @Test
    void findAll() {
        Column column1 = ColumnBuilder.oneColumn().build();
        Column column2 = ColumnBuilder.oneColumn().build();
        when(columnRepository.findAll()).thenReturn(List.of(column1, column2));

        List<Column> columnsFound = columnService.findAll();

        assertThat(columnsFound).hasSize(2).isEqualTo(List.of(column1, column2));
    }

    @Test
    void findAllColumnsNotInBoard() {
        Column column1 = ColumnBuilder.oneColumn().withStatus(StatusEnum.POOL).build();
        Column column2 = ColumnBuilder.oneColumn().withStatus(StatusEnum.BACKLOG).build();
        when(columnRepository.findAllByStatusIn(List.of(StatusEnum.POOL, StatusEnum.BACKLOG))).thenReturn(List.of(column1, column2));

        List<Column> columnsFound = columnService.findAllColumnsNotInBoard();

        assertThat(columnsFound).hasSize(2).isEqualTo(List.of(column1, column2));
    }

    @Test
    void findAllColumnsInBoard() {
        Column column1 = ColumnBuilder.oneColumn().withStatus(StatusEnum.IN_PROGRESS).build();
        Column column2 = ColumnBuilder.oneColumn().withStatus(StatusEnum.IN_PROGRESS).build();
        Column column3 = ColumnBuilder.oneColumn().withStatus(StatusEnum.PAUSED).build();
        Column column4 = ColumnBuilder.oneColumn().withStatus(StatusEnum.PAUSED).build();
        Column column5 = ColumnBuilder.oneColumn().withStatus(StatusEnum.DONE).build();
        List<Column> expectedColumns = List.of(column1, column2, column3, column4, column5);
        when(columnRepository.findAllByStatusIn(List.of(StatusEnum.IN_PROGRESS, StatusEnum.PAUSED, StatusEnum.DONE)))
                .thenReturn(expectedColumns);

        List<Column> columnsFound = columnService.findAllColumnsInBoard();

        assertThat(columnsFound).hasSize(5).isEqualTo(expectedColumns);
    }

    @Test
    void findAllInProgress() {
        Column column1 = ColumnBuilder.oneColumn().withStatus(StatusEnum.IN_PROGRESS).build();
        Column column2 = ColumnBuilder.oneColumn().withStatus(StatusEnum.IN_PROGRESS).build();
        when(columnRepository.findAllByStatusIn(List.of(StatusEnum.IN_PROGRESS)))
                .thenReturn(List.of(column1, column2));

        List<Column> columnsFound = columnService.findAllInProgress();

        assertThat(columnsFound).hasSize(2).isEqualTo(List.of(column1, column2));
    }

    @Test
    void saveColumn() {
        Column column = ColumnBuilder.oneColumn().build();
        when(columnRepository.save(column)).thenReturn(column);

        Column columnSaved = columnService.save(column);

        assertEquals(column, columnSaved);
    }

    @Test
    void removeColumnsWithoutVocabularies() {
        Vocabulary vocabulary1 = VocabularyBuilder.oneVocabulary().build();
        Vocabulary vocabulary2 = VocabularyBuilder.oneVocabulary().build();
        Column column1 = ColumnBuilder.oneColumn().withVocabularies(List.of(vocabulary1, vocabulary2)).build();
        Column column2 = ColumnBuilder.oneColumn().withVocabularies(List.of(vocabulary1)).build();
        Column column3 = ColumnBuilder.oneColumn().build();

        List<Column> columnsFiltered = columnService.removeColumnsWithoutVocabularies(List.of(column1, column2, column3));

        assertThat(columnsFiltered).hasSize(2).isEqualTo(List.of(column1, column2));
    }

    @Test
    void findAllInProgressToPractice() {
        Vocabulary vocabulary1 = VocabularyBuilder.oneVocabulary().build();
        Vocabulary vocabulary2 = VocabularyBuilder.oneVocabulary().build();
        Column column1 = ColumnBuilder.oneColumn().withVocabularies(List.of(vocabulary1, vocabulary2)).build();
        Column column2 = ColumnBuilder.oneColumn().withVocabularies(List.of(vocabulary1)).build();
        Column column3 = ColumnBuilder.oneColumn().build();
        when(columnService.findAllInProgress()).thenReturn(List.of(column1, column2, column3));
        when(columnService.removeColumnsWithoutVocabularies(List.of(column1, column2, column3))).thenReturn(List.of(column1, column2));

        List<Column> columnsFiltered = columnService.findAllInProgressToPractice();

        assertThat(columnsFiltered).hasSize(2).isEqualTo(List.of(column1, column2));
    }
}

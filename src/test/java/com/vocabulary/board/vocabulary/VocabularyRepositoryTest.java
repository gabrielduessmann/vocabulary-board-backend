package com.vocabulary.board.vocabulary;

import com.vocabulary.board.builders.ColumnBuilder;
import com.vocabulary.board.builders.VocabularyBuilder;
import com.vocabulary.board.column.Column;
import com.vocabulary.board.column.ColumnRepository;
import com.vocabulary.board.column.enums.StatusEnum;
import com.vocabulary.board.utils.JpaUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class VocabularyRepositoryTest {

    @Autowired
    private VocabularyRepository vocabularyRepository;

    @Autowired
    private ColumnRepository columnRepository;

    @Test
    public void findByColumnId() {
        Column column1 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().build());
        Column column2 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().build());
        Vocabulary vocabulary1 = (Vocabulary) JpaUtil.save(vocabularyRepository,
                VocabularyBuilder.oneVocabulary().withColumn(column1).build());
        Vocabulary vocabulary2 = (Vocabulary) JpaUtil.save(vocabularyRepository,
                VocabularyBuilder.oneVocabulary().withColumn(column2).build());
        Vocabulary vocabulary3 = (Vocabulary) JpaUtil.save(vocabularyRepository,
                VocabularyBuilder.oneVocabulary().withColumn(column1).build());

        List<Vocabulary> vocabulariesFound = vocabularyRepository.findByColumnId(column1.getId());

        assertThat(vocabulariesFound).hasSize(2).isEqualTo(List.of(vocabulary1, vocabulary3));
    }

    @Test
    public void findByColumnId_null() {
        Column column1 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().build());
        Column column2 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().build());
        Vocabulary vocabulary1 = (Vocabulary) JpaUtil.save(vocabularyRepository,
                VocabularyBuilder.oneVocabulary().withColumn(column1).build());
        Vocabulary vocabulary2 = (Vocabulary) JpaUtil.save(vocabularyRepository,
                VocabularyBuilder.oneVocabulary().withColumn(column1).build());
        Vocabulary vocabulary3 = (Vocabulary) JpaUtil.save(vocabularyRepository,
                VocabularyBuilder.oneVocabulary().withColumn(column1).build());

        List<Vocabulary> vocabulariesFound = vocabularyRepository.findByColumnId(column2.getId());

        assertThat(vocabulariesFound).isEmpty();
    }

    @Test
    public void moveColumn_bySprintOrder() {
        Column column1 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withSprintOrder(1).build());
        Column column2 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withSprintOrder(2).build());
        Vocabulary vocabulary1 = (Vocabulary) JpaUtil.save(vocabularyRepository,
                VocabularyBuilder.oneVocabulary().withColumn(column1).build());

        assertDoesNotThrow(() -> vocabularyRepository.moveColumn(vocabulary1.getId(), null, 2));
    }

    @Test
    public void moveColumn_bySprintOrder_exception() {
        Column column1 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withSprintOrder(1).build());
        Column column2 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withSprintOrder(2).build());
        Vocabulary vocabulary1 = (Vocabulary) JpaUtil.save(vocabularyRepository,
                VocabularyBuilder.oneVocabulary().withColumn(column1).build());

        assertThrows(JpaObjectRetrievalFailureException.class, () -> vocabularyRepository.moveColumn(vocabulary1.getId(), null, 3));
    }

    @Test
    public void moveColumn_byStatus() {
        Column column1 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.POOL).build());
        Column column2 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.BACKLOG).build());
        Vocabulary vocabulary1 = (Vocabulary) JpaUtil.save(vocabularyRepository,
                VocabularyBuilder.oneVocabulary().withColumn(column1).build());

       assertDoesNotThrow(() -> vocabularyRepository.moveColumn(vocabulary1.getId(), StatusEnum.BACKLOG, null));
    }

    @Test
    public void moveColumn_byStatus_exception() {
        Column column1 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.POOL).build());
        Column column2 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.BACKLOG).build());
        Vocabulary vocabulary1 = (Vocabulary) JpaUtil.save(vocabularyRepository,
                VocabularyBuilder.oneVocabulary().withColumn(column1).build());

        assertThrows(JpaObjectRetrievalFailureException.class, () -> vocabularyRepository.moveColumn(vocabulary1.getId(), StatusEnum.DONE, null));
    }
}

package com.vocabulary.board.vocabulary;

import java.security.InvalidParameterException;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import com.vocabulary.board.column.Column;
import com.vocabulary.board.column.ColumnRepository;
import com.vocabulary.board.column.enums.StatusEnum;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class VocabularyService {

    @Autowired
    private VocabularyRepository vocabRepository;

    @Autowired
    private ColumnRepository columnRepository;

    private Integer lastSprintOrder = null;

    public Vocabulary addVocabulary(Vocabulary vocab) {
        return vocabRepository.save(vocab);
    }

    public Vocabulary updateVocabulary(Vocabulary vocabulary) {
        Vocabulary currentVocabulary = vocabRepository.findById(vocabulary.getId()).orElseThrow(InvalidParameterException::new);
        currentVocabulary.setWord(vocabulary.getWord());
        currentVocabulary.setDescription(vocabulary.getDescription());
        return vocabRepository.save(currentVocabulary);
    }

    public Vocabulary saveNewVocabulary(Vocabulary vocabulary) {
        try {
            Column column = columnRepository.findByStatus(StatusEnum.POOL);
            if (column == null) throw new ServiceException("There is no column with POOL status");
            vocabulary.setColumn(column);
            return vocabRepository.save(vocabulary);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new ServiceException("There is more than one column with status POOL");
        }
    }

    public Optional<Vocabulary> getOneVocabulary(UUID id) {
        return vocabRepository.findById(id);
    }

    public List<Vocabulary> getAllVocabularies() {
        List<Vocabulary> vocabs = new ArrayList<>();
        vocabRepository.findAllByOrderByWordAsc().forEach(vocabs::add);
        return vocabs;
    }

    public List<Vocabulary> findVocabulariesByColumnId(UUID columnId) {
        List<Vocabulary> vocabs = new ArrayList<>();
        vocabRepository.findByColumnId(columnId).forEach(vocabs::add);
        return vocabs;
    }

    public Vocabulary moveToNextColumn(UUID vocabularyId) {
        Vocabulary vocabulary = vocabRepository.findById(vocabularyId).orElseThrow();
        Column columnToMove = whichColumnToMove(vocabulary.getColumn());

        return vocabRepository.moveColumn(vocabulary.getId(), columnToMove.getStatus(), columnToMove.getSprintOrder());
    }

    public Column whichColumnToMove(Column currentColumn) {
        Column columnToMove = new Column();
        columnToMove.setStatus(null);
        columnToMove.setSprintOrder(null);

        StatusEnum currentStatus = currentColumn.getStatus();
        Integer currentSprintOrder = currentColumn.getSprintOrder();

        if (lastSprintOrder == null && currentStatus == StatusEnum.IN_PROGRESS) {
            lastSprintOrder =  columnRepository.getMaxSprintOrder();
        }

        if (currentStatus == StatusEnum.BACKLOG || (currentStatus == StatusEnum.IN_PROGRESS && currentSprintOrder != lastSprintOrder) || currentStatus == StatusEnum.PAUSED) {
            columnToMove.setSprintOrder(currentSprintOrder + 1);
        } else if (currentStatus == StatusEnum.POOL) {
            columnToMove.setStatus(StatusEnum.BACKLOG);
        } else if (currentSprintOrder == lastSprintOrder && currentStatus == StatusEnum.IN_PROGRESS) {
            columnToMove.setStatus(StatusEnum.DONE);
        }

        return columnToMove;
    }
}

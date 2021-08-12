package com.vocabulary.board.vocabulary;

import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
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

    private Integer lastSpringOrder = null; // FIXME - possibility of static

    public Vocabulary addVocabulary(Vocabulary vocab) {
        return vocabRepository.save(vocab);
    }

    public VocabularyDTO updateVocabulary(VocabularyDTO vocabularyDto) {
        Vocabulary vocabulary = vocabRepository.findById(vocabularyDto.getId()).orElseThrow();
        vocabulary.setWord(vocabularyDto.getWord());
        vocabulary.setDescription(vocabularyDto.getDescription());
        vocabRepository.save(vocabulary);
        return vocabularyDto;
    }

    public VocabularyDTO saveNewVocabulary(VocabularyDTO vocabularyDto) {
        try {
            Column column = columnRepository.findByStatus(StatusEnum.POOL);
            Vocabulary vocabulary = vocabularyDto.toEntity();
            vocabulary.setCreationDate(new Date());
            vocabulary.setColumn(column);
            vocabRepository.save(vocabulary);
            return vocabularyDto;
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new ServiceException("There is more than one column with status in Pool. " +
                    "Only one column can have this status");
        }
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

    public Vocabulary moveToNextColumn(UUID vocabularyId) {
        StatusEnum targetStatus = null;
        Integer targetSpringOrder = null;

        Vocabulary vocabulary = vocabRepository.findById(vocabularyId).orElseThrow();
        StatusEnum currentStatus = vocabulary.getColumn().getStatus();
        Integer currentSprintOrder = vocabulary.getColumn().getSprintOrder();

        if (lastSpringOrder == null && currentStatus == StatusEnum.IN_PROGRESS) {
            // lastSpringOrder = columnRepository.getLastSprintOrder();  TODO - implement this method to search for the max number where status = IN_PROGRESS
            lastSpringOrder = 10;
        }

        if (currentStatus == StatusEnum.BACKLOG || (currentStatus == StatusEnum.IN_PROGRESS && currentSprintOrder != lastSpringOrder) || currentStatus == StatusEnum.PAUSED) {
            targetSpringOrder = currentSprintOrder + 1;
        } else if (currentStatus == StatusEnum.POOL) {
            targetStatus = StatusEnum.BACKLOG;
        } else if (currentSprintOrder == lastSpringOrder && currentStatus == StatusEnum.IN_PROGRESS) {
            targetStatus = StatusEnum.DONE;
        }

        return vocabRepository.moveColumn(vocabulary.getId(), targetStatus, targetSpringOrder);
    }
}

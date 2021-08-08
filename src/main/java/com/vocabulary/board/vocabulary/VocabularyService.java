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

    public Vocabulary addVocabulary(Vocabulary vocab) {
        return vocabRepository.save(vocab);
    }

    public Vocabulary updateVocabulary(VocabularyDTO vocabularyDto) {
        Vocabulary vocabulary = vocabRepository.findById(vocabularyDto.getId()).orElseThrow();
        vocabulary.setWord(vocabularyDto.getWord());
        vocabulary.setDescription(vocabularyDto.getDescription());
        return vocabRepository.save(vocabulary);
    }

    public Vocabulary saveNewVocabulary(VocabularyDTO vocabularyDto) {
        try {
            Column column = columnRepository.findByStatus(StatusEnum.POOL);
            Vocabulary vocabulary = vocabularyDto.toEntity();
            vocabulary.setCreationDate(new Date());
            vocabulary.setColumn(column);
            return vocabRepository.save(vocabulary);
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
}

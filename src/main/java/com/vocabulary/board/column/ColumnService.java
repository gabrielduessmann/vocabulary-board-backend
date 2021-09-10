package com.vocabulary.board.column;

import com.vocabulary.board.column.enums.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ColumnService {

    @Autowired
    private ColumnRepository columnRepository;

    public Optional<Column> findById(UUID id) {
        return columnRepository.findById(id);
    }

    public List<Column> findAll() {
        List<Column> columns= new ArrayList<>();
        columnRepository.findAll().forEach(columns::add);
        return columns;
    }

    public List<Column> findAllColumnsNotInBoard() {
        List<Column> columns = new ArrayList<>();
        List<StatusEnum> status = List.of(StatusEnum.POOL, StatusEnum.BACKLOG);
        columnRepository.findAllByStatusIn(status).forEach(columns::add);
        return columns;
    }

    public List<Column> findAllColumnsInBoard() {
        List<Column> columns = new ArrayList<>();
        List<StatusEnum> status = List.of(StatusEnum.IN_PROGRESS, StatusEnum.PAUSED, StatusEnum.DONE);
        columnRepository.findAllByStatusIn(status).forEach(columns::add);
        return columns;
    }

    public List<Column> findAllInProgress() {
        List<StatusEnum> status = List.of(StatusEnum.IN_PROGRESS);
        return columnRepository.findAllByStatusIn(status);
    }

    public List<Column> findAllInProgressToPractice() {
        return removeColumnsWithoutVocabularies(findAllInProgress());
    }

    public Column save(Column column) {
        return columnRepository.save(column);
    }

    public List<Column> removeColumnsWithoutVocabularies(List<Column> columns) {
        return columns
                .stream()
                .filter(column -> column.getVocabularies() != null && column.getVocabularies().size() > 0)
                .collect(Collectors.toList());
    }

    public List<Column> findColumnsByStatus(List<StatusEnum> statusList) {
        return columnRepository.findAllByStatusIn(statusList);
    }
}

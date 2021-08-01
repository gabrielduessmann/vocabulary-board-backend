package com.vocabulary.board.column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Column save(Column column) {
        return columnRepository.save(column);
    }
}
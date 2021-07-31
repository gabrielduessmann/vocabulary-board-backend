package com.vocabulary.board.column;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ColumnRepository extends CrudRepository<Column, UUID> {
    List<Column> findAllByOOrderBySprintOrderAscSprintOrderIs();
    List<Column> findAllBySprintOrderIsNotNullAndOrderBySprintOrderAsc();
    List<Column> findAllByStatus();
}

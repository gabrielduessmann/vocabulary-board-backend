package com.vocabulary.board.column;

import com.vocabulary.board.column.enums.StatusEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ColumnRepository extends CrudRepository<Column, UUID>, ColumnRepositoryCustom {
    List<Column> findAllByStatusIn(List<StatusEnum> status);

    Column findByStatus(StatusEnum statusEnum);
}

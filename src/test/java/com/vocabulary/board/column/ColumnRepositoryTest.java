package com.vocabulary.board.column;

import com.vocabulary.board.builders.ColumnBuilder;
import com.vocabulary.board.column.enums.StatusEnum;
import com.vocabulary.board.utils.JpaUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
public class ColumnRepositoryTest {

    @Autowired
    private ColumnRepository columnRepository;

    @Test
    public void getMaxSprintOrder() {
        JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.IN_PROGRESS).withSprintOrder(1).build());
        JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.IN_PROGRESS).withSprintOrder(2).build());
        JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.IN_PROGRESS).withSprintOrder(3).build());
        JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.PAUSED).withSprintOrder(4).build());
        JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.DONE).withSprintOrder(5).build());

        Integer maxSprintOrder = columnRepository.getMaxSprintOrder();

        assertEquals(3, maxSprintOrder);
    }

    @Test
    public void getMaxSprintOrder_mull() {
        JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.BACKLOG).withSprintOrder(1).build());
        JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.PAUSED).withSprintOrder(2).build());
        JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.DONE).withSprintOrder(3).build());
        JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.BACKLOG).withSprintOrder(0).build());

        Integer maxSprintOrder = columnRepository.getMaxSprintOrder();

        assertNull(maxSprintOrder);
    }

    @Test
    public void findAllByStatusIn() {
        Column column1 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.POOL).build());
        Column column2 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.BACKLOG).build());
        Column column3 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.IN_PROGRESS).build());
        Column column4 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.IN_PROGRESS).build());
        Column column5 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.PAUSED).build());
        Column column6 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.DONE).build());

        List<StatusEnum> statusSearch = List.of(StatusEnum.IN_PROGRESS, StatusEnum.PAUSED, StatusEnum.DONE);

        List<Column> columnsSearch = columnRepository.findAllByStatusIn(statusSearch);

        assertThat(columnsSearch).hasSize(4).isEqualTo(List.of(column3, column4, column5, column6));
    }

    @Test
    public void findByStatus() {
        Column column1 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.POOL).build());
        Column column2 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.BACKLOG).build());
        Column column3 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.IN_PROGRESS).build());
        Column column4 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.IN_PROGRESS).build());
        Column column5 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.PAUSED).build());
        Column column6 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.DONE).build());

        Column columnsSearch = columnRepository.findByStatus(StatusEnum.DONE);

        assertEquals(column6, columnsSearch);
    }

    @Test
    public void findByStatus_null() {
        Column column1 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.BACKLOG).build());
        Column column2 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.IN_PROGRESS).build());
        Column column3 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.IN_PROGRESS).build());
        Column column4 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.PAUSED).build());
        Column column5 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.DONE).build());

        Column columnsSearch = columnRepository.findByStatus(StatusEnum.POOL);

        assertNull(columnsSearch);
    }
}

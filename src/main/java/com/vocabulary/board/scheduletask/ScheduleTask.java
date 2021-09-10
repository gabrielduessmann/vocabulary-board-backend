package com.vocabulary.board.scheduletask;

import com.vocabulary.board.column.Column;
import com.vocabulary.board.column.ColumnService;
import com.vocabulary.board.column.enums.StatusEnum;
import com.vocabulary.board.vocabulary.VocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableScheduling
public class ScheduleTask {

    private final List<StatusEnum> MOVE_VOCABULARIES_IN_COLUMNS_STATUS = List.of(StatusEnum.BACKLOG, StatusEnum.IN_PROGRESS, StatusEnum.PAUSED);

    @Autowired
    private ColumnService columnService;

    @Autowired
    private VocabularyService vocabularyService;

    /*
     * Run scheduled task to move the vocabularies to the next sprint (column)
     * At 08:00 on Monday
     * https://crontab.guru/#0_8_*_*_1
     */
    @Scheduled(cron = "* 0 8 * * 1")
    public void scheduleTaskToMoveVocabulariesToNextSprint() {

        List<Column> columns =  columnService.findColumnsByStatus(MOVE_VOCABULARIES_IN_COLUMNS_STATUS);

        // FIXME - get columns in order, therefore reverse won't be necessary
        Collections.reverse(columns);

        columns.forEach(column -> {
                    vocabularyService.findVocabulariesByColumnId(column.getId())
                            .forEach(vocabulary -> {
                                vocabularyService.moveToNextColumn(vocabulary.getId());
                            });
                });

    }

}

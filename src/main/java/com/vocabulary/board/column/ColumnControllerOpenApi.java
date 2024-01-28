package com.vocabulary.board.column;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface ColumnControllerOpenApi {

    @ApiOperation("Find a Column by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok")
    })
    ResponseEntity<Column> findById(@PathVariable UUID id);

    ResponseEntity<List<Column>> findAll();

    ResponseEntity<List<Column>> findAllNotInBoard();

    ResponseEntity<List<Column>> findAllInBoard();

    ResponseEntity<List<Column>> findAllInProgress();

    ResponseEntity<List<Column>> findAllInProgressToPractice();

    ResponseEntity<Column> save(@RequestBody Column column);
}

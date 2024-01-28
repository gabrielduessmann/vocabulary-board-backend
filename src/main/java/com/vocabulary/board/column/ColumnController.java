package com.vocabulary.board.column;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*") // FIXME - Remove when deploy to Heroku
@Api(value = "ColumnController", tags = "My Controller" )
@RestController
public class ColumnController implements ColumnControllerOpenApi {

    @Autowired
    private ColumnService columnService;

    @GetMapping("column/{id}")
    @Override
    public ResponseEntity<Column> findById(@PathVariable UUID id) {
        return columnService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("columns")
    @Override
    public ResponseEntity<List<Column>> findAll() {
        return ResponseEntity.ok(columnService.findAll());
    }

    @GetMapping("columns/not-in-board")
    @Override
    public ResponseEntity<List<Column>> findAllNotInBoard() {
       return ResponseEntity.ok(columnService.findAllColumnsNotInBoard());
    }

    @GetMapping("columns/in-board")
    @Override
    public ResponseEntity<List<Column>> findAllInBoard() {
        return ResponseEntity.ok(columnService.findAllColumnsInBoard());
    }

    @GetMapping("columns/in-progress")
    @Override
    public ResponseEntity<List<Column>> findAllInProgress() {
        return ResponseEntity.ok(columnService.findAllInProgress());
    }

    @GetMapping("columns/in-progress-practice")
    @Override
    public ResponseEntity<List<Column>> findAllInProgressToPractice() {
        return ResponseEntity.ok(columnService.findAllInProgressToPractice());
    }

    @PostMapping("column")
    @Override
    public ResponseEntity<Column> save(@RequestBody Column column) {
        return ResponseEntity.ok(columnService.save(column));
    }
}

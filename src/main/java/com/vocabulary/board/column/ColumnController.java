package com.vocabulary.board.column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*") // FIXME - Remove when deploy to Heroku
@RestController
public class ColumnController {

    @Autowired
    private ColumnService columnService;

    @GetMapping("column/{id}")
    public ResponseEntity<Column> findById(@PathVariable UUID id) {
        return columnService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("columns")
    public ResponseEntity<List<Column>> findAll() {
        return ResponseEntity.ok(columnService.findAll());
    }

    @PostMapping("column")
    public ResponseEntity<Column> save(@RequestBody Column column) {
        return ResponseEntity.ok(columnService.save(column));
    }
}

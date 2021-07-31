package com.vocabulary.board.vocabulary;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*") // FIXME - Remove when deploy to Heroku
@RestController
public class VocabularyController {

    @Autowired
    private VocabularyService vocabService;

    @PostMapping("/vocabulary")
    public ResponseEntity<Vocabulary> addVocabulary(@RequestBody Vocabulary vocab) {
        return ResponseEntity.ok(vocabService.addVocabulary(vocab));
    }

    @GetMapping("/vocabulary/{id}")
    public ResponseEntity<Vocabulary> getVocabularyById(@PathVariable UUID id) {
        return vocabService.getOneVocabulary(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/vocabularies")
    public ResponseEntity<List<Vocabulary>> getAllVocabularies() {
        return ResponseEntity.ok(vocabService.getAllVocabularies());
    }

    @GetMapping("vocabularies/column/{columnId}")
    public ResponseEntity<List<Vocabulary>> findAllVocabulariesByColumnId(@PathVariable  UUID columnId) {
        return ResponseEntity.ok(vocabService.findVocabulariesByColumnId(columnId));
    }

}

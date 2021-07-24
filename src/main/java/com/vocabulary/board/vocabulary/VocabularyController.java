package com.vocabulary.board.vocabulary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Vocabulary> getVocabularyById(@PathVariable String id) {
        return ResponseEntity.ok(vocabService.getOneVocabulary(id));
    }

    @GetMapping("/vocabularies")
    public ResponseEntity<List<Vocabulary>> getAllVocabularies() {
        return ResponseEntity.ok(vocabService.getAllVocabularies());
    }

}

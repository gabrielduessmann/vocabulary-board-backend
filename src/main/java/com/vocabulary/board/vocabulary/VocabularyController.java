package com.vocabulary.board.vocabulary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class VocabularyController {

    @Autowired
    private VocabularyService vocabService;

    @PostMapping("/vocabulary")
    public void addVocabulary(@RequestBody Vocabulary vocab) {
        vocabService.addVocabulary(vocab);
    }

    @GetMapping("/vocabulary/{id}")
    public Vocabulary getVocabularyById(@PathVariable String id) {
        return vocabService.getOneVocabulary(id);
    }

}

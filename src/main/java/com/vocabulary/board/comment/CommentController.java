package com.vocabulary.board.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*") // FIXME - Remove when deploy to Heroku
@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/vocabulary/{vocabularyId}/comments")
    public ResponseEntity<List<Comment>> findAllVocabularyComments(@PathVariable UUID vocabularyId) {
        return ResponseEntity.ok(commentService.findAllVocabularyComments(vocabularyId));
    }

    @PostMapping("/comment")
    public ResponseEntity<Comment> saveComment(@RequestBody Comment comment) {
        return ResponseEntity.ok(commentService.saveComment(comment));
    }
}

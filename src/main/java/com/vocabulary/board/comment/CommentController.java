package com.vocabulary.board.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

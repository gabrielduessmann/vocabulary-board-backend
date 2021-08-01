package com.vocabulary.board.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public List<Comment> findAllVocabularyComments(UUID vocabularyId) {
        List<Comment> comments = new ArrayList<>();
        commentRepository.findAllByVocabularyId(vocabularyId).forEach(comments::add);
        return comments;
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

}

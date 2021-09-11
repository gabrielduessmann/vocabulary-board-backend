package com.vocabulary.board.comment;

import com.vocabulary.board.messagebroker.RabbitMQService;
import messagebroker.RabbitMQConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RabbitMQService rabbitMQService;

    public List<Comment> findAllVocabularyComments(UUID vocabularyId) {
        List<Comment> comments = new ArrayList<>();
        commentRepository.findAllByVocabularyId(vocabularyId).forEach(comments::add);
        return comments;
    }

    public Comment saveComment(Comment comment) {
        rabbitMQService.sendMessage(RabbitMQConstants.VOCABULARY_QUEUE, VocabularyPracticedDtoConverter.toDto(comment.getVocabulary()));
        return commentRepository.save(comment);
    }

}

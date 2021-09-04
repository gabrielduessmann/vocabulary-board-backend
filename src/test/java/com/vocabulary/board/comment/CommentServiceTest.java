package com.vocabulary.board.comment;

import com.vocabulary.board.builders.CommentBuilder;
import com.vocabulary.board.builders.VocabularyBuilder;
import com.vocabulary.board.vocabulary.Vocabulary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    private final Vocabulary VOCABULARY = VocabularyBuilder.oneVocabulary().build();
    private final Comment COMMENT = new Comment(UUID.randomUUID(), "I can go everything with this jacket", new Date(), VOCABULARY);

    @Test
    void findAllVocabularyComments() {
        Comment comment = CommentBuilder.oneComment().withVocabulary(VOCABULARY).build();
        when(commentRepository.findAllByVocabularyId(VOCABULARY.getId())).thenReturn(List.of(COMMENT, comment));

        List<Comment> commentsFound = commentRepository.findAllByVocabularyId(VOCABULARY.getId());

        assertThat(commentsFound).hasSize(2).isEqualTo(List.of(COMMENT, comment));
    }

    @Test
    void saveComment() {
        when(commentRepository.save(COMMENT)).thenReturn(COMMENT);

        Comment commentSaved = commentService.saveComment(COMMENT);

        assertEquals(COMMENT, commentSaved);
    }

}

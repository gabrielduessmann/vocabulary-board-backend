package com.vocabulary.board.comment;

import com.vocabulary.board.vocabulary.Vocabulary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue
    private UUID id;

    private String comment;

    private Date commentedDate;

    @ManyToOne
    private Vocabulary vocabulary;

    public Comment(String comment, Date commentedDate) {
        this.comment = comment;
        this.commentedDate = commentedDate;
    }

}

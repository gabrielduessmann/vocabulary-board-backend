package com.vocabulary.board.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vocabulary.board.vocabulary.Vocabulary;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.UUID;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = true) // FIXME change to false
    private Date commentedDate;


    @ManyToOne
    private Vocabulary vocabulary;

    public Comment() {
    }

    public Comment(String comment, Date commentedDate) {
        this.comment = comment;
        this.commentedDate = commentedDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCommentedDate() {
        return commentedDate;
    }

    public void setCommentedDate(Date commentedDate) {
        this.commentedDate = commentedDate;
    }

    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(Vocabulary vocabulary) {
        this.vocabulary = vocabulary;
    }
}

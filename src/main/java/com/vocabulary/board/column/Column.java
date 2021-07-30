package com.vocabulary.board.column;

import com.vocabulary.board.column.enums.StatusEnum;
import com.vocabulary.board.vocabulary.Vocabulary;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Column {

    @Id
    @GeneratedValue
    private UUID id;

    @javax.persistence.Column(nullable = false)
    private String title;

    private Date nextUpdate;

    @Enumerated(EnumType.STRING)
    @javax.persistence.Column(nullable = false)
    private StatusEnum status;

    private Integer sprintOrder;

    @OneToMany
    private List<Vocabulary> vocabularies;

    public Column() {
    }

    public Column(String title, Date nextUpdate, StatusEnum status, Integer sprintOrder) {
        this.title = title;
        this.nextUpdate = nextUpdate;
        this.status = status;
        this.sprintOrder = sprintOrder;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getNextUpdate() {
        return nextUpdate;
    }

    public void setNextUpdate(Date nextUpdate) {
        this.nextUpdate = nextUpdate;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Integer getSprintOrder() {
        return sprintOrder;
    }

    public List<Vocabulary> getVocabularies() {
        return vocabularies;
    }

    public void setVocabularies(List<Vocabulary> vocabularies) {
        this.vocabularies = vocabularies;
    }

    public void setSprintOrder(Integer sprintOrder) {
        this.sprintOrder = sprintOrder;
    }
}
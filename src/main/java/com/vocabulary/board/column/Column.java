package com.vocabulary.board.column;

import com.vocabulary.board.column.enums.StatusEnum;
import com.vocabulary.board.vocabulary.Vocabulary;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Column {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @javax.persistence.Column(nullable = false)
    private String title;

    @javax.persistence.Column
    private Date nextUpdate;

    @Enumerated(EnumType.STRING)
    @javax.persistence.Column(nullable = false)
    private StatusEnum status;

    @javax.persistence.Column(nullable = false)
    private Integer sprintOrder;

    @OneToMany
    private List<Vocabulary> vocabularies;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
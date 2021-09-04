package com.vocabulary.board.column;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vocabulary.board.column.enums.StatusEnum;
import com.vocabulary.board.vocabulary.Vocabulary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.EnumType;
import javax.persistence.OneToMany;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
// Observation: Postgres didn't allow "column" as table name.
@Entity(name="columnBoard")
public class Column {

    @Id
    @GeneratedValue
    private UUID id;

    private String title;

    private Date nextUpdate;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    private Integer sprintOrder;

    @JsonIgnore
    @OneToMany(mappedBy = "column")
    private List<Vocabulary> vocabularies;

    public Column(String title, Date nextUpdate, StatusEnum status, Integer sprintOrder) {
        this.title = title;
        this.nextUpdate = nextUpdate;
        this.status = status;
        this.sprintOrder = sprintOrder;
    }
}

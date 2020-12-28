package com.hva.MaaltijdMaat.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Document
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Meal {

    @Id
    private String id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String description;

    @DBRef
    private Group group;

    @DBRef
    private User createdBy;

    @DBRef
    private List<Suggestion> suggestions;
}

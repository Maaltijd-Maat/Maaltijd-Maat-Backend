package com.hva.MaaltijdMaat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Document
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Meal {

    @Id
    private String id;

    private String title;

    private ZonedDateTime start;

    private ZonedDateTime end;

    private String description;

    @DBRef
    private Group group;

    @DBRef
    private User createdBy;

    @DBRef
    private List<MealSuggestion> suggestions;
}

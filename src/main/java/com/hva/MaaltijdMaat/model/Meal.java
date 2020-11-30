package com.hva.MaaltijdMaat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Meal {

    @Id
    private String id;

    private LocalDateTime plannedFor;

    @DBRef
    private User createdBy;

    @DBRef
    private List<MealSuggestion> suggestions;
}

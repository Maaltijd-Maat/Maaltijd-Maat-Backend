package com.hva.MaaltijdMaat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "suggestion")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Suggestion {
    @Id
    private String id;
    private String title;
    private String description;

    @DBRef
    private User suggester;

    @DBRef
    private Dish dish;
}

package com.hva.MaaltijdMaat.model;

import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Dish {

    @Id
    private String id;

    private String name;

    @DBRef
    private User author;

    private int amountOfPeople;

    private String[] instructions;

    private Ingredient[] ingredients;
}

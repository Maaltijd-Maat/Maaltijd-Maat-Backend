package com.hva.MaaltijdMaat.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dish {

    @Id
    @Getter
    private String id;

    @Getter
    private String name;

    @DBRef(db = "user")
    @Getter
    private User author;

    @Getter
    private int amountOfPeople;

    @Getter
    private String[] instructions;

    @Getter
    private Ingredient[] ingredients;
}

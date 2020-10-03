package com.hva.MaaltijdMaat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@NoArgsConstructor
@AllArgsConstructor
public class Dish {

    @Id
    @Getter
    private ObjectId id;

    @Getter
    @Setter
    private String name;

    @DBRef(db = "user")
    @Getter
    private User author;

    @Getter
    @Setter
    private int amountOfPeople;

    @Getter
    @Setter
    private String[] instructions;

    @Getter
    @Setter
    private Ingredient[] ingredients;
}

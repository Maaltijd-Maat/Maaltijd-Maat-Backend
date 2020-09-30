package com.hva.MaaltijdMaat.model;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
public class Dish {

    @Id
    private ObjectId id;

    private String name;

    @DBRef
    private User user;

    private int amountOfPeople;

    private String[] instructions;

    private Ingredient[] ingredients;

    public Dish() {}

    public Dish(String name, User user, int amountOfPeople, String[] instructions, Ingredient[] ingredients) {
        this.name = name;
        this.user = user;
        this.amountOfPeople = amountOfPeople;
        this.instructions = instructions;
        this.ingredients = ingredients;
    }

    public ObjectId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getAmountOfPeople() {
        return amountOfPeople;
    }

    public void setAmountOfPeople(int amountOfPeople) {
        this.amountOfPeople = amountOfPeople;
    }

    public String[] getInstructions() {
        return instructions;
    }

    public void setInstructions(String[] instructions) {
        this.instructions = instructions;
    }

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredient[] ingredients) {
        this.ingredients = ingredients;
    }
}

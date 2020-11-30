package com.hva.MaaltijdMaat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MealSuggestion {
    @DBRef
    private User suggester;

    @DBRef
    private Dish dish;
}

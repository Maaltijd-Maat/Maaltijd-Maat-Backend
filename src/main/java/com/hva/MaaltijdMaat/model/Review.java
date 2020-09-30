package com.hva.MaaltijdMaat.model;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
public class Review {
    @Id
    private Long reviewId;
    private double rating;
    private String content;
    private User author;
    private Dish dish;
}

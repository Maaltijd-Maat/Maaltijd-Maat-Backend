package com.hva.MaaltijdMaat.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @Getter
    private Long reviewId;

    @Getter
    private double rating;

    @Getter
    private String content;

    @Getter
    private User author;

    @Getter
    private Dish dish;
}

package com.hva.MaaltijdMaat.model;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@AllArgsConstructor
public class Dish {
    @Id
    private Long dishId;
    private String name;
    private List<String> steps;
}

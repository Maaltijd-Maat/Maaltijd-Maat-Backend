package com.hva.MaaltijdMaat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Attendee {
    @Id
    private String id;

    private String status;

    @DBRef
    private Meal meal;

    @DBRef
    private User attendee;
}

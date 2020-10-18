package com.hva.MaaltijdMaat.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Document
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    private String id;

    private String name;

    @DBRef(db = "user")
    private User owner;

    @DBRef(db = "user")
    private List<User> members = new ArrayList<>();
}

package com.hva.MaaltijdMaat.model;

import com.hva.MaaltijdMaat.enums.Allergen;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@Document
@Data
public class User {

    @Id
    @Getter
    private ObjectId id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String avatar;
    private boolean guest;
    private List<Allergen> Allergenen;
}

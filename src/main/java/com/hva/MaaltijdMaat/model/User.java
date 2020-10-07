package com.hva.MaaltijdMaat.model;

import com.hva.MaaltijdMaat.enums.Allergen;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Getter
    private String id;

    @Getter
    private String firstname;

    @Getter
    private String lastname;

    @Getter
    private String email;

    @Getter
    private String password;

    @Getter
    private String avatar;

    @Getter
    private boolean guest;

    @Getter
    private List<Allergen> Allergenen;
}

package com.hva.MaaltijdMaat.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "passwordResetToken")
public class PasswordResetToken {
    @Id
    private String id;

    private String token;

    @DBRef
    private User user;

    private Date expiryDate;
}

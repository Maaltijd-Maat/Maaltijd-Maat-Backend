package com.hva.MaaltijdMaat.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Getter
@Setter
@Builder
@Document
public class Invite {
    @Id
    private String id;

    @DBRef
    private User inviter;

    @DBRef
    private Group group;

    @DBRef
    private User invitee;

    @Field
    @Indexed(name="expireIndex", expireAfterSeconds=604800)
    private Date expireDate;
}

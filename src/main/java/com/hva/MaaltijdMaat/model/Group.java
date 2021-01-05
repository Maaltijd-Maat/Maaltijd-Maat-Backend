package com.hva.MaaltijdMaat.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    private String id;

    private String name;

    private String color;

    @DBRef
    private User owner;

    @DBRef
    private List<User> members = new ArrayList<>();

    @DBRef
    private List<Meal> meals = new ArrayList<>();

    /**
     * Adds a new member to the list of members if the member is not already a member of the group.
     *
     * @param newMember new member to be added
     * @return true if member is added; false if member is not added
     */
    public boolean addMember(User newMember) {
        if (!members.contains(newMember)) {
            members.add(newMember);
            return true;
        }

        return false;
    }
}

package com.hva.MaaltijdMaat.model;

import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Objects;

@Document(collection = "user")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    private String id;

    private String color;

    private String firstname;

    private String lastname;

    @Indexed(unique = true)
    private String email;

    private String secondEmail;

    private String password;

    private String avatar;

    private boolean guest;

    private String[] allergies;

    private String[] diets;

    public String getFullName(){
        return this.firstname + " " + this.getLastname();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return guest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

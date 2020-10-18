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

@Document(collection = "user")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    private String id;

    private String firstname;

    private String lastname;

    @Indexed(unique = true)
    private String email;

    private String password;

    private String avatar;

    private boolean guest;

    private String[] Allergenen;

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
    public boolean equals(Object o){
        if(o instanceof User){
            User toCompare = (User) o;
            return this.email.equals(toCompare.email);
        }

        return false;
    }
}

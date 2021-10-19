package com.myproject.myproject.data;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Entity
@Table(name = "users")
@Data

public class User implements UserDetails {
    
    private static final long serialVersionUID = 1l;


    @Id // for primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // for autoincrement
    private int id;
    // @Size(min = 2, message = "name should be more than 2 character")
    private String name;
    // @Size(min = 3, message = "username must be 3 characters")
    private String username;
    @Size(min = 2, message = "The email should be than 2 character and should contain @ symbol")
    private String email;
    @Size(min = 4, message = "The password should be more than 3 character")
    private String password;
    // Its not part of the table
    @Transient
    private String confirmPassword;
    // @Size(min = 2, message = "The stream must be more than 2 character")
    private String stream;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    
}

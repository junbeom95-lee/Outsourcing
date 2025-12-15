package com.example.outsourcing.domain.auth.model.dto;

import com.example.outsourcing.common.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class UserinfoDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }
    public Long getUserId() {return user.getId();}

    @Override
    public String getUsername() {return user.getUsername();}

    @Override
    public String getPassword() {return user.getPassword();}
}

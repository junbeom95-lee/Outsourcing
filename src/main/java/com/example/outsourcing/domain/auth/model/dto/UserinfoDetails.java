package com.example.outsourcing.domain.auth.model.dto;

import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.enums.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class UserinfoDetails implements UserDetails {

    @Getter
    private final Long userId;
    private final String username;
    private final UserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {return this.username;}

    //password 사용 안함
    @Override
    public String getPassword() {return null;}
}

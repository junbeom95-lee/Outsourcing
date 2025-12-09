package com.example.outsourcing.domain.auth.service;

import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.enums.ExceptionCode;
import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.domain.auth.model.dto.UserinfoDetails;
import com.example.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserinfoDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_USER));

        return new UserinfoDetails(user);
    }
}

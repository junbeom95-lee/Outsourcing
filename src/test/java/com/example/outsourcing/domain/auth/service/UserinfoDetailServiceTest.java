package com.example.outsourcing.domain.auth.service;

import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.enums.ExceptionCode;
import com.example.outsourcing.common.enums.UserRole;
import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserinfoDetailServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserinfoDetailService userinfoDetailService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("testUser", "test@test.com", "1234", "jung-hyuk", UserRole.USER);

    }

    @Test
    @DisplayName("데이터 베이스에 해당 유저명이 있는지 확인")
    void loadUserByUsername_findUser() {
        // given
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        // when
        UserDetails userDetails = userinfoDetailService.loadUserByUsername("testUser");

        // then
        assertThat(userDetails.getUsername()).isEqualTo("testUser");

    }

    @Test
    @DisplayName("데이터 베이스에 해당 유저명이 없으면 예외 발생")
    void loadUserByUsername_UserNotFound() {
        // given
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        // when & then
        CustomException exception = Assertions.assertThrows(CustomException.class, () -> {userinfoDetailService.loadUserByUsername("unknown");});

        assertThat(exception.getExceptionCode()).isEqualTo(ExceptionCode.NOT_FOUND_USER);
    }



    }

package com.example.outsourcing.domain.team.service;

import com.example.outsourcing.common.entity.Team;
import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.entity.User_Team;
import com.example.outsourcing.common.enums.UserRole;
import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.team.model.request.TeamCreateRequest;
import com.example.outsourcing.domain.team.model.request.TeamUpdateRequest;
import com.example.outsourcing.domain.team.model.response.*;
import com.example.outsourcing.domain.team.repository.TeamRepository;
import com.example.outsourcing.domain.user.repository.UserRepository;
import com.example.outsourcing.domain.user_team.reposiotry.UserTeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserTeamRepository userTeamRepository;

    @InjectMocks
    private TeamService teamService;

    private Team testTeam;
    private User testUser;

    private User adminUser;
    private User normalUser;

    private final String testTeamName = "testTeam";
    private final String updateTeamName = "updatedTeam";
    private final String testDescription = "testDescription";
    private final String updateDescription = "updatedDescription";
    private final String adminRole = "ADMIN";
    private final String userRole = "USER";


    @BeforeEach
    void setUp() {

        // 팀
        testTeam = new Team(testTeamName, testDescription);

        ReflectionTestUtils.setField(testTeam, "id", 1L);

        ReflectionTestUtils.setField(testTeam, "userTeamList", new ArrayList<>());


        // 관리자
        adminUser = new User(
                "adminUser",
                "admin@test.com",
                "1234",
                "admin",
                UserRole.ADMIN
        );
        ReflectionTestUtils.setField(adminUser, "id", 10L);

        // 일반유저
        normalUser = new User(
                "normalUser",
                "normal@test.com",
                "1234",
                "normal",
                UserRole.USER
        );
        ReflectionTestUtils.setField(normalUser, "id", 20L);

    }


    // 팀 생성
    @Test
    @DisplayName("팀 생성 성공 - 관리자")
    void createTeam_success() {

        // given
        TeamCreateRequest request = new TeamCreateRequest(testTeamName, testDescription);

        when(teamRepository.existsByName(testTeamName)).thenReturn(false);
        when(teamRepository.save(any(Team.class))).thenReturn(testTeam);

        // when
        CommonResponse<TeamCreateResponse> response = teamService.create(adminRole, request);

        // then
        assertTrue(response.isSuccess());
        assertEquals(testTeamName, response.getData().getName());
        verify(teamRepository, times(1)).save(any(Team.class));
    }

    @Test
    @DisplayName("팀 생성 실패 - 일반유저 - 권한 x")
    void crete_Team_fail_noPermission() {

        // given
        TeamCreateRequest request = new TeamCreateRequest(testTeamName, testDescription);

        // when & then
        assertThrows(CustomException.class,
                () -> teamService.create(userRole, request));
    }

    @Test
    @DisplayName("팀 생성 실패 - 팀 이름 중복")
    void createTeam_fail_duplicateNAme() {

        // given
        TeamCreateRequest request = new TeamCreateRequest(testTeamName, testDescription);

        when(teamRepository.existsByName(testTeamName)).thenReturn(true);

        // when & then
        assertThrows(CustomException.class,
                () -> teamService.create(adminRole, request));
    }


    // 팀 수정
    @Test
    @DisplayName("팀 수정 성공 - 관리자")
    void updateTeam_success() {

        // given
        TeamUpdateRequest request = new TeamUpdateRequest();

        ReflectionTestUtils.setField(request, "name", updateTeamName);
        ReflectionTestUtils.setField(request, "description", updateDescription);

        when(teamRepository.existsByName(any())).thenReturn(false);
        when(teamRepository.findById(any())).thenReturn(Optional.of(testTeam));
        when(teamRepository.findAllUserByTeamId(any())).thenReturn(List.of(adminUser));

        // when
        CommonResponse<TeamUpdateResponse> response = teamService.update(adminRole, 1L, request);

        // then
        assertTrue(response.isSuccess());
        assertEquals(updateTeamName, response.getData().getName());

    }

    @Test
    @DisplayName("팀 수정 실패 - 팀이 존재하지 않음")
    void updateTeam_notFound() {

        // given
        TeamUpdateRequest request = new TeamUpdateRequest();
        when(teamRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(CustomException.class,
                () -> teamService.update(adminRole, 1L, request));
    }


    // 팀 삭제
    @Test
    @DisplayName("팀 삭제 성공 - 관리자 + 멤버 없음")
    void deleteTeam_success() {

        // given
        when(teamRepository.findById(1L)).thenReturn(Optional.of(testTeam));

        // when
        CommonResponse<Void> response = teamService.delete(adminRole, 1L);

        // then
        assertTrue(response.isSuccess());
        verify(teamRepository).delete(testTeam);

    }

    @Test
    @DisplayName("팀 삭제 실패 - 일반 유저")
    void deleteTema_fail_noPermission() {

        // given
//        when(teamRepository.findById(1L)).thenReturn(Optional.of(testTeam));

        // when
        assertThrows(CustomException.class,
                () -> teamService.delete(userRole, 1L));
    }

    @Test
    @DisplayName("팀 삭제 실패 - 팀에 멤버 존재")
    void deleteTema_fail_hasMembers() {

        // given
        when(teamRepository.findById(1L)).thenReturn(Optional.of(testTeam));
        when(teamRepository.findAllUserByTeamId(1L)).thenReturn(List.of(adminUser));

        // when
        assertThrows(CustomException.class,
                () -> teamService.delete(adminRole, 1L));
    }

    @Test
    @DisplayName("팀 삭제 실패 - 팀이 존재하지 않을때")
    void deleteTema_fail_notFound() {

        // given
        when(teamRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(CustomException.class,
                () -> teamService.delete(adminRole, 1L));
    }


    // 팀 목록 조회
    @Test
    @DisplayName("팀 목록 조회 성공")
    void getTeamList_success() {

        // given
        when(teamRepository.findAllWithUsers()).thenReturn(List.of(testTeam));

        // when
        CommonResponse<List<TeamGetListResponse>> response = teamService.getTeamList();

        // then
        assertEquals(1, response.getData().size());
        verify(teamRepository).findAllWithUsers();
    }


    // 팀 상세 조회
    @Test
    @DisplayName("팀 상세 조회 성공")
    void getTeamDetail_success() {

        // given
        when(teamRepository.findByIdWithUsers(1L)).thenReturn(Optional.of(testTeam));

        // when
        CommonResponse<TeamGetDetailResponse> response = teamService.getTeamDetail(1L);

        // then
        assertEquals(testTeamName, response.getData().getName());
    }


    @Test
    @DisplayName("팀 상세 조회 실패 - 팀 없음")
    void getTeamDetail_notFound() {

        // given
        when(teamRepository.findByIdWithUsers(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(CustomException.class,
                () -> teamService.getTeamDetail(1L));
    }


    // 팀 멤버 조회
    @Test
    @DisplayName("팀 멤버 조회 성공")
    void getTeamMembers_success() {

        // given
        when(userTeamRepository.findUsersByTeamId(1L)).thenReturn(List.of(adminUser, normalUser));

        // when
        CommonResponse<List<TeamGetMemberResponse>> response = teamService.getTemMemberList(1L);

        // then
        assertEquals(2, response.getData().size());
    }



}
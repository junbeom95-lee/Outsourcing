package com.example.outsourcing.domain.search.service;

import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.search.dto.SearchResponse;
import com.example.outsourcing.domain.search.dto.TaskSearchResponse;
import com.example.outsourcing.domain.search.dto.TeamSearchResponse;
import com.example.outsourcing.domain.search.dto.UserSearchResponse;
import com.example.outsourcing.domain.task.repository.TaskRepository;
import com.example.outsourcing.domain.team.repository.TeamRepository;
import com.example.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final TaskRepository taskRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommonResponse<SearchResponse> search(String query) {

        if (query == null || query.isBlank()) {
            return new CommonResponse<>(false, "검색어를 입력하세요", null);
        }

        List<TaskSearchResponse> taskResponse = taskRepository.findAllByTitleContaining(query)
                .stream()
                .map(TaskSearchResponse::from)
                .toList();

        List<TeamSearchResponse> teamResponse = teamRepository.findAllByNameContaining(query)
                .stream()
                .map(TeamSearchResponse::from)
                .toList();

        List<UserSearchResponse> userResponse = userRepository.findAllByNameContaining(query)
                .stream()
                .map(UserSearchResponse::from)
                .toList();

        SearchResponse response = new SearchResponse(taskResponse, teamResponse, userResponse);

        return new CommonResponse<>(true , "검색 성공", response);

    }
}

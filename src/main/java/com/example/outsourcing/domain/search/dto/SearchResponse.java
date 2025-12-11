package com.example.outsourcing.domain.search.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SearchResponse {

    private final List<TaskSearchResponse> tasks;
    private final List<TeamSearchResponse> teams;
    private final List<UserSearchResponse> users;
}

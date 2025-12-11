package com.example.outsourcing.domain.search.controller;

import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.search.dto.SearchResponse;
import com.example.outsourcing.domain.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/api/search")
    public ResponseEntity<CommonResponse<SearchResponse>> getSearch(@RequestParam("query") String query){

        CommonResponse<SearchResponse> response = searchService.search(query);

        HttpStatus status = response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status).body(response);
    }
}

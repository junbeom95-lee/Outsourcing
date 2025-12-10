package com.example.outsourcing.domain.team.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamCreateRequest {

    @NotBlank(message = "팀명은 필수입니다.")
    private String name;

    @NotBlank
    private String description;

    public TeamCreateRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

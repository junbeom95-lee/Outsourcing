package com.example.outsourcing.domain.team.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TeamUpdateRequest {
    @NotBlank(message = "팀명은 필수입니다.")
    private String name;

    private String description;

}

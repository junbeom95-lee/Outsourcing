package com.example.outsourcing.domain.dashboard.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class DashboardWeelkyResponse {
    private final String name;
    private final int tasks;
    private final int completed;
    private final LocalDate date;
}

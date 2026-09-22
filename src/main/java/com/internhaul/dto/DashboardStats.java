package com.internhaul.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DashboardStats {

    private long totalInterns;
    private long activeProjects;
    private long availableInterns;
    private long pendingAllocations;
}
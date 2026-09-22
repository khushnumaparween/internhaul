package com.internhaul.service;

import com.internhaul.dto.DashboardStats;
import com.internhaul.entity.AllocationStatus;
import com.internhaul.repository.AllocationRepository;
import com.internhaul.repository.InternRepository;
import com.internhaul.repository.ProjectRepository;
import org.springframework.stereotype.Service;


@Service
public class DashboardService {

    private final InternRepository internRepository;
    private final ProjectRepository projectRepository;
    private final AllocationRepository allocationRepository;

    public DashboardService(
            InternRepository internRepository,
            ProjectRepository projectRepository,
            AllocationRepository allocationRepository) {

        this.internRepository = internRepository;
        this.projectRepository = projectRepository;
        this.allocationRepository = allocationRepository;
    }

    public DashboardStats getStats() {

        long totalInterns =
                internRepository.count();

        long activeProjects =
                projectRepository.countByActiveTrue();

        long availableInterns =
                internRepository.countByAvailableTrue();

        long pendingAllocations =
                allocationRepository.countByStatus(
                        AllocationStatus.PENDING);

        return DashboardStats.builder()
                .totalInterns(totalInterns)
                .activeProjects(activeProjects)
                .availableInterns(availableInterns)
                .pendingAllocations(pendingAllocations)
                .build();
    }
}
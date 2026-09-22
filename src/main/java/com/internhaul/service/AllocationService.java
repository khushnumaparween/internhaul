package com.internhaul.service;

import com.internhaul.dto.AllocationResponse;
import com.internhaul.entity.Allocation;
import com.internhaul.entity.AllocationStatus;
import com.internhaul.entity.Intern;
import com.internhaul.entity.Project;
import com.internhaul.repository.AllocationRepository;
import com.internhaul.repository.InternRepository;
import com.internhaul.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AllocationService {

    private final AllocationRepository allocationRepository;
    private final InternRepository internRepository;
    private final ProjectRepository projectRepository;
    private final SettingsService settingsService;

    public AllocationService(
            AllocationRepository allocationRepository,
            InternRepository internRepository,
            ProjectRepository projectRepository,
            SettingsService settingsService) {

        this.allocationRepository = allocationRepository;
        this.internRepository = internRepository;
        this.projectRepository = projectRepository;
        this.settingsService = settingsService;
    }

    @Transactional
    public AllocationResponse createAllocation(
            Long internId,
            Long projectId) {

        Intern intern = internRepository.findById(internId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Intern not found with id: " + internId));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Project not found with id: " + projectId));

        if (!intern.isAvailable()) {

            throw new RuntimeException(
                    "Intern is not available");
        }

        if (settingsService
                .isDuplicateProtectionEnabled()) {

            if (allocationRepository
                    .existsByInternIdAndStatus(
                            internId,
                            AllocationStatus.PENDING)) {

                throw new RuntimeException(
                        "Intern already has a pending allocation");
            }

            if (allocationRepository
                    .existsByInternIdAndStatus(
                            internId,
                            AllocationStatus.APPROVED)) {

                throw new RuntimeException(
                        "Intern is already allocated to a project");
            }
        }

        if (!project.isActive()) {

            throw new RuntimeException(
                    "Project is not active");
        }

        if (settingsService
                .isCapacityProtectionEnabled()
                && project.getAvailableSlots() <= 0) {

            throw new RuntimeException(
                    "No available slots in this project");
        }

        AllocationStatus initialStatus =
                settingsService.isApprovalRequired()
                        ? AllocationStatus.PENDING
                        : AllocationStatus.APPROVED;

        Allocation allocation =
                Allocation.builder()
                        .intern(intern)
                        .project(project)
                        .status(initialStatus)
                        .allocatedAt(
                                LocalDateTime.now())
                        .build();

        if (initialStatus ==
                AllocationStatus.APPROVED) {

            intern.setAvailable(false);

            internRepository.save(intern);

            if (settingsService
                    .isCapacityProtectionEnabled()
                    || project.getAvailableSlots() > 0) {

                project.setAvailableSlots(
                        project.getAvailableSlots() - 1
                );

                projectRepository.save(project);
            }
        }

        Allocation savedAllocation =
                allocationRepository.save(allocation);

        return toResponse(savedAllocation);
    }

    public List<AllocationResponse> getAllAllocations() {

        return allocationRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<AllocationResponse> getAllocationsByStatus(
            AllocationStatus status) {

        return allocationRepository.findByStatus(status)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public AllocationResponse getAllocationById(
            Long id) {

        Allocation allocation =
                findAllocationById(id);

        return toResponse(allocation);
    }

    private Allocation findAllocationById(
            Long id) {

        return allocationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Allocation not found with id: " + id));
    }

    public List<AllocationResponse> getAllocationsByIntern(
            Long internId) {

        return allocationRepository
                .findByInternId(internId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<AllocationResponse> getAllocationsByProject(
            Long projectId) {

        return allocationRepository
                .findByProjectId(projectId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public AllocationResponse approveAllocation(
            Long allocationId) {

        Allocation allocation =
                findAllocationById(allocationId);

        if (allocation.getStatus()
                != AllocationStatus.PENDING) {

            throw new RuntimeException(
                    "Only pending allocations can be approved");
        }

        Intern intern =
                allocation.getIntern();

        Project project =
                allocation.getProject();

        if (!intern.isAvailable()) {

            throw new RuntimeException(
                    "Intern is no longer available");
        }

        if (settingsService
                .isCapacityProtectionEnabled()
                && project.getAvailableSlots() <= 0) {

            throw new RuntimeException(
                    "No available slots in this project");
        }

        allocation.setStatus(
                AllocationStatus.APPROVED);

        intern.setAvailable(false);

        internRepository.save(intern);

        if (settingsService
                .isCapacityProtectionEnabled()
                || project.getAvailableSlots() > 0) {

            project.setAvailableSlots(
                    project.getAvailableSlots() - 1
            );

            projectRepository.save(project);
        }

        Allocation savedAllocation =
                allocationRepository.save(
                        allocation);

        return toResponse(savedAllocation);
    }

    @Transactional
    public AllocationResponse rejectAllocation(
            Long allocationId) {

        Allocation allocation =
                findAllocationById(allocationId);

        if (allocation.getStatus()
                != AllocationStatus.PENDING) {

            throw new RuntimeException(
                    "Only pending allocations can be rejected");
        }

        allocation.setStatus(
                AllocationStatus.REJECTED);

        Allocation savedAllocation =
                allocationRepository.save(
                        allocation);

        return toResponse(savedAllocation);
    }

    private AllocationResponse toResponse(
            Allocation allocation) {

        return AllocationResponse.builder()
                .id(allocation.getId())
                .internId(
                        allocation.getIntern().getId())
                .internName(
                        allocation.getIntern().getName())
                .projectId(
                        allocation.getProject().getId())
                .projectName(
                        allocation.getProject().getName())
                .status(
                        allocation.getStatus().name())
                .allocatedAt(
                        allocation.getAllocatedAt())
                .build();
    }
}
package com.internhaul.repository;

import com.internhaul.entity.Allocation;
import com.internhaul.entity.AllocationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AllocationRepository
        extends JpaRepository<Allocation, Long> {

    List<Allocation> findByInternId(Long internId);

    List<Allocation> findByProjectId(Long projectId);

    long countByProjectIdAndStatus(
            Long projectId,
            AllocationStatus status);

    long countByStatus(AllocationStatus status);

    boolean existsByInternIdAndStatus(
            Long internId,
            AllocationStatus status);

    List<Allocation> findByStatus(AllocationStatus status);
}
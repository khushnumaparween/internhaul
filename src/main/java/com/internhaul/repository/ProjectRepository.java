package com.internhaul.repository;

import com.internhaul.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    long countByActiveTrue();

    Optional<Project> findFirstByActiveTrue();
}
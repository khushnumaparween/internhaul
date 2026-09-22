package com.internhaul.repository;

import com.internhaul.entity.ProjectSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectSkillRepository
        extends JpaRepository<ProjectSkill, Long> {

    List<ProjectSkill> findByProjectId(Long projectId);

    List<ProjectSkill> findBySkillId(Long skillId);

    boolean existsByProjectIdAndSkillId(
            Long projectId,
            Long skillId
    );
}
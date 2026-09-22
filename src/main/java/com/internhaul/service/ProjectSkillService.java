package com.internhaul.service;

import com.internhaul.entity.Project;
import com.internhaul.entity.ProjectSkill;
import com.internhaul.entity.Skill;
import com.internhaul.repository.ProjectRepository;
import com.internhaul.repository.ProjectSkillRepository;
import com.internhaul.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectSkillService {

    private final ProjectSkillRepository projectSkillRepository;
    private final ProjectRepository projectRepository;
    private final SkillRepository skillRepository;

    public ProjectSkillService(
            ProjectSkillRepository projectSkillRepository,
            ProjectRepository projectRepository,
            SkillRepository skillRepository) {

        this.projectSkillRepository = projectSkillRepository;
        this.projectRepository = projectRepository;
        this.skillRepository = skillRepository;
    }

    public ProjectSkill addSkillToProject(
            Long projectId,
            Long skillId,
            boolean required,
            Integer priority) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Project not found with id: " + projectId));

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Skill not found with id: " + skillId));

        if (projectSkillRepository
                .existsByProjectIdAndSkillId(projectId, skillId)) {

            throw new RuntimeException(
                    "Skill already assigned to this project");
        }

        ProjectSkill projectSkill = ProjectSkill.builder()
                .project(project)
                .skill(skill)
                .required(required)
                .priority(priority)
                .build();

        return projectSkillRepository.save(projectSkill);
    }

    public List<ProjectSkill> getSkillsByProject(Long projectId) {
        return projectSkillRepository.findByProjectId(projectId);
    }

    public void removeSkillFromProject(Long id) {

        if (!projectSkillRepository.existsById(id)) {
            throw new RuntimeException(
                    "Project skill not found with id: " + id);
        }

        projectSkillRepository.deleteById(id);
    }
}

package com.internhaul.service;

import com.internhaul.entity.AllocationStatus;
import com.internhaul.entity.Project;
import com.internhaul.repository.AllocationRepository;
import com.internhaul.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final AllocationRepository allocationRepository;

    public ProjectService(
            ProjectRepository projectRepository,
            AllocationRepository allocationRepository) {

        this.projectRepository = projectRepository;
        this.allocationRepository = allocationRepository;
    }

    public Project createProject(Project project) {

        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {

        return projectRepository.findAll();
    }

    public Project getCurrentProject() {

        return projectRepository.findFirstByActiveTrue()
                .orElse(null);
    }

    public Project getProjectById(Long id) {

        return projectRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Project not found with id: " + id));
    }

    public Project updateProject(
            Long id,
            Project updatedProject) {

        Project existingProject =
                getProjectById(id);

        existingProject.setName(
                updatedProject.getName());

        existingProject.setDescription(
                updatedProject.getDescription());

        existingProject.setTechnology(
                updatedProject.getTechnology());

        existingProject.setLocation(
                updatedProject.getLocation());

        existingProject.setAvailableSlots(
                updatedProject.getAvailableSlots());

        existingProject.setActive(
                updatedProject.isActive());

        return projectRepository.save(existingProject);
    }

    public void deleteProject(Long id) {

        Project project =
                getProjectById(id);

        projectRepository.delete(project);
    }

    public long getAllocatedInternCount(Long projectId) {

        return allocationRepository
                .countByProjectIdAndStatus(
                        projectId,
                        AllocationStatus.APPROVED
                );
    }

    public long getRemainingSlots(Long projectId) {

        Project project =
                getProjectById(projectId);

        return project.getAvailableSlots();
    }
}
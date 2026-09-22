package com.internhaul.controller;

import com.internhaul.entity.ProjectSkill;
import com.internhaul.service.ProjectSkillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectSkillController {

    private final ProjectSkillService projectSkillService;

    public ProjectSkillController(
            ProjectSkillService projectSkillService) {

        this.projectSkillService = projectSkillService;
    }

    @PostMapping("/{projectId}/skills/{skillId}")
    public ResponseEntity<ProjectSkill> addSkillToProject(
            @PathVariable Long projectId,
            @PathVariable Long skillId,
            @RequestParam boolean required,
            @RequestParam Integer priority) {

        ProjectSkill projectSkill =
                projectSkillService.addSkillToProject(
                        projectId,
                        skillId,
                        required,
                        priority
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(projectSkill);
    }

    @GetMapping("/{projectId}/skills")
    public ResponseEntity<List<ProjectSkill>> getProjectSkills(
            @PathVariable Long projectId) {

        return ResponseEntity.ok(
                projectSkillService
                        .getSkillsByProject(projectId)
        );
    }

    @DeleteMapping("/skills/{id}")
    public ResponseEntity<Void> removeSkill(
            @PathVariable Long id) {

        projectSkillService.removeSkillFromProject(id);

        return ResponseEntity.noContent().build();
    }
}

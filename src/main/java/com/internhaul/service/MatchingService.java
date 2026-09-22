package com.internhaul.service;

import com.internhaul.dto.MatchResult;
import com.internhaul.entity.Intern;
import com.internhaul.entity.InternSkill;
import com.internhaul.entity.Project;
import com.internhaul.entity.ProjectSkill;
import com.internhaul.repository.InternRepository;
import com.internhaul.repository.InternSkillRepository;
import com.internhaul.repository.ProjectRepository;
import com.internhaul.repository.ProjectSkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchingService {

    private final InternRepository internRepository;
    private final ProjectRepository projectRepository;
    private final InternSkillRepository internSkillRepository;
    private final ProjectSkillRepository projectSkillRepository;
    private final SettingsService settingsService;

    public MatchingService(
            InternRepository internRepository,
            ProjectRepository projectRepository,
            InternSkillRepository internSkillRepository,
            ProjectSkillRepository projectSkillRepository,
            SettingsService settingsService) {

        this.internRepository = internRepository;
        this.projectRepository = projectRepository;
        this.internSkillRepository = internSkillRepository;
        this.projectSkillRepository = projectSkillRepository;
        this.settingsService = settingsService;
    }

    public MatchResult calculateMatch(
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

        List<InternSkill> internSkills =
                internSkillRepository.findByInternId(internId);

        List<ProjectSkill> projectSkills =
                projectSkillRepository.findByProjectId(projectId);

        int totalRequiredSkills = 0;
        int matchedSkills = 0;
        int actualProficiency = 0;

        for (ProjectSkill projectSkill : projectSkills) {

            if (!projectSkill.isRequired()) {
                continue;
            }

            totalRequiredSkills++;

            int requiredSkillId =
                    projectSkill.getSkill().getId().intValue();

            for (InternSkill internSkill : internSkills) {

                int internSkillId =
                        internSkill.getSkill().getId().intValue();

                if (requiredSkillId == internSkillId) {

                    matchedSkills++;

                    Integer proficiency =
                            internSkill.getProficiency();

                    if (proficiency != null) {
                        actualProficiency += proficiency;
                    }

                    break;
                }
            }
        }

        double skillCoverageScore =
                totalRequiredSkills == 0
                        ? 0
                        : (matchedSkills * 100.0)
                        / totalRequiredSkills;

        double proficiencyScore =
                totalRequiredSkills == 0
                        ? 0
                        : (actualProficiency * 100.0)
                        / (totalRequiredSkills * 5);

        return MatchResult.builder()
                .internId(intern.getId())
                .projectId(project.getId())
                .internName(intern.getName())
                .projectName(project.getName())
                .matchedSkills(matchedSkills)
                .totalRequiredSkills(totalRequiredSkills)
                .skillCoverageScore(skillCoverageScore)
                .proficiencyScore(proficiencyScore)
                .available(intern.isAvailable())
                .build();
    }

    public List<MatchResult> findBestMatches(
            Long projectId) {

        projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Project not found with id: " + projectId));

        List<Intern> interns =
                internRepository.findAll();

        List<MatchResult> results =
                interns.stream()
                        .filter(Intern::isAvailable)
                        .map(intern ->
                                calculateMatch(
                                        intern.getId(),
                                        projectId))
                        .toList();

        double skillWeight =
                settingsService.getSkillWeight() / 100.0;

        double proficiencyWeight =
                settingsService.getProficiencyWeight() / 100.0;

        results.forEach(result -> {

            double overallScore =
                    result.getSkillCoverageScore()
                            * skillWeight

                            +

                            result.getProficiencyScore()
                                    * proficiencyWeight;

            result.setOverallScore(
                    overallScore
            );
        });

        return results.stream()
                .sorted((a, b) ->
                        Double.compare(
                                b.getOverallScore(),
                                a.getOverallScore()))
                .toList();
    }
}
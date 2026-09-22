package com.internhaul.service;

import com.internhaul.entity.Intern;
import com.internhaul.entity.InternSkill;
import com.internhaul.entity.Skill;
import com.internhaul.repository.InternSkillRepository;
import com.internhaul.repository.InternRepository;
import com.internhaul.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InternSkillService {

    private final InternSkillRepository internSkillRepository;
    private final InternRepository internRepository;
    private final SkillRepository skillRepository;

    public InternSkillService(
            InternSkillRepository internSkillRepository,
            InternRepository internRepository,
            SkillRepository skillRepository) {

        this.internSkillRepository = internSkillRepository;
        this.internRepository = internRepository;
        this.skillRepository = skillRepository;
    }

    public InternSkill addSkillToIntern(
            Long internId,
            Long skillId,
            Integer proficiency) {

        Intern intern = internRepository.findById(internId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Intern not found with id: " + internId));

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Skill not found with id: " + skillId));

        if (internSkillRepository
                .existsByInternIdAndSkillId(internId, skillId)) {

            throw new RuntimeException(
                    "Skill already assigned to this intern");
        }

        InternSkill internSkill = InternSkill.builder()
                .intern(intern)
                .skill(skill)
                .proficiency(proficiency)
                .build();

        return internSkillRepository.save(internSkill);
    }

    public List<InternSkill> getSkillsByIntern(Long internId) {

        return internSkillRepository.findByInternId(internId);
    }

    public void removeSkillFromIntern(Long id) {

        if (!internSkillRepository.existsById(id)) {
            throw new RuntimeException(
                    "Intern skill not found with id: " + id);
        }

        internSkillRepository.deleteById(id);
    }
}

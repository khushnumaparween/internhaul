package com.internhaul.service;

import com.internhaul.entity.Skill;
import com.internhaul.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill createSkill(Skill skill) {

        return skillRepository
                .findByNameIgnoreCase(skill.getName())
                .orElseGet(() -> skillRepository.save(skill));
    }

    public List<Skill> getAllSkills() {

        return skillRepository.findAll();
    }

    public Skill getSkillById(Long id) {

        return skillRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Skill not found with id: " + id));
    }

    public void deleteSkill(Long id) {

        Skill skill = getSkillById(id);

        skillRepository.delete(skill);
    }
}
package com.internhaul.controller;

import com.internhaul.entity.InternSkill;
import com.internhaul.service.InternSkillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interns")
public class InternSkillController {

    private final InternSkillService internSkillService;

    public InternSkillController(
            InternSkillService internSkillService) {

        this.internSkillService = internSkillService;
    }

    @PostMapping("/{internId}/skills/{skillId}")
    public ResponseEntity<InternSkill> addSkillToIntern(

            @PathVariable Long internId,
            @PathVariable Long skillId,
            @RequestParam Integer proficiency) {

        InternSkill internSkill =
                internSkillService.addSkillToIntern(
                        internId,
                        skillId,
                        proficiency
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(internSkill);
    }

    @GetMapping("/{internId}/skills")
    public ResponseEntity<List<InternSkill>> getInternSkills(

            @PathVariable Long internId) {

        return ResponseEntity.ok(
                internSkillService
                        .getSkillsByIntern(internId)
        );
    }

    @DeleteMapping("/skills/{id}")
    public ResponseEntity<Void> removeSkill(

            @PathVariable Long id) {

        internSkillService.removeSkillFromIntern(id);

        return ResponseEntity.noContent().build();
    }
}
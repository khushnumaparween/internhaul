package com.internhaul.repository;

import com.internhaul.entity.InternSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternSkillRepository
        extends JpaRepository<InternSkill, Long> {

    List<InternSkill> findByInternId(Long internId);

    List<InternSkill> findBySkillId(Long skillId);

    boolean existsByInternIdAndSkillId(
            Long internId,
            Long skillId
    );
}

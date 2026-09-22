package com.internhaul.entity;

import jakarta.persistence.*;
        import lombok.*;

@Entity
@Table(
        name = "intern_skills",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"intern_id", "skill_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intern_id", nullable = false)
    private Intern intern;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    private Integer proficiency;
}

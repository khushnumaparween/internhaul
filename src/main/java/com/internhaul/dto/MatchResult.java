package com.internhaul.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchResult {

    private Long internId;
    private Long projectId;

    private String internName;
    private String projectName;

    private int matchedSkills;
    private int totalRequiredSkills;

    private double skillCoverageScore;
    private double proficiencyScore;
    private double overallScore;

    private boolean available;
}
package com.internhaul.controller;

import com.internhaul.dto.MatchResult;
import com.internhaul.service.MatchingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matching")
public class MatchingController {

    private final MatchingService matchingService;

    public MatchingController(MatchingService matchingService) {
        this.matchingService = matchingService;
    }

    @GetMapping("/intern/{internId}/project/{projectId}")
    public ResponseEntity<MatchResult> calculateMatch(
            @PathVariable Long internId,
            @PathVariable Long projectId) {

        return ResponseEntity.ok(
                matchingService.calculateMatch(
                        internId,
                        projectId
                )
        );



    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<MatchResult>> findBestMatches(
            @PathVariable Long projectId) {

        return ResponseEntity.ok(
                matchingService.findBestMatches(projectId));
    }
}
package com.internhaul.controller;

import com.internhaul.service.SettingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.Map;

@RestController
@RequestMapping("/api/settings")
public class SettingsController {

    private final SettingsService settingsService;

    public SettingsController(
            SettingsService settingsService) {

        this.settingsService = settingsService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getSettings() {

        return ResponseEntity.ok(
                Map.of(
                        "approvalRequired",
                        settingsService.isApprovalRequired(),

                        "duplicateProtection",
                        settingsService
                                .isDuplicateProtectionEnabled(),

                        "capacityProtection",
                        settingsService
                                .isCapacityProtectionEnabled(),

                        "skillWeight",
                        settingsService.getSkillWeight(),

                        "proficiencyWeight",
                        settingsService
                                .getProficiencyWeight()
                )
        );
    }

    @PutMapping
    public ResponseEntity<?> updateSettings(
            @RequestBody Map<String, Object> request) {

        try {

            boolean approvalRequired =
                    Boolean.parseBoolean(
                            String.valueOf(
                                    request.get("approvalRequired")
                            )
                    );

            boolean duplicateProtection =
                    Boolean.parseBoolean(
                            String.valueOf(
                                    request.get("duplicateProtection")
                            )
                    );

            boolean capacityProtection =
                    Boolean.parseBoolean(
                            String.valueOf(
                                    request.get("capacityProtection")
                            )
                    );

            double skillWeight =
                    Double.parseDouble(
                            String.valueOf(
                                    request.get("skillWeight")
                            )
                    );

            double proficiencyWeight =
                    Double.parseDouble(
                            String.valueOf(
                                    request.get("proficiencyWeight")
                            )
                    );

            settingsService.updateSettings(
                    approvalRequired,
                    duplicateProtection,
                    capacityProtection,
                    skillWeight,
                    proficiencyWeight
            );

            return ResponseEntity.ok(
                    Map.of(
                            "message",
                            "Settings updated successfully"
                    )
            );

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(
                    Map.of(
                            "message",
                            e.getMessage()
                    )
            );
        }
    }
}

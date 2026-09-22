package com.internhaul.service;

import com.internhaul.entity.SystemSetting;
import com.internhaul.repository.SystemSettingRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SettingsService {

    public static final String APPROVAL_REQUIRED =
            "approval_required";

    public static final String DUPLICATE_PROTECTION =
            "duplicate_allocation_protection";

    public static final String CAPACITY_PROTECTION =
            "project_capacity_protection";

    public static final String SKILL_WEIGHT =
            "skill_coverage_weight";

    public static final String PROFICIENCY_WEIGHT =
            "proficiency_weight";

    private final SystemSettingRepository settingRepository;

    public SettingsService(
            SystemSettingRepository settingRepository) {

        this.settingRepository = settingRepository;
    }

    @Transactional
    public void initializeDefaults() {

        createIfMissing(
                APPROVAL_REQUIRED,
                "true"
        );

        createIfMissing(
                DUPLICATE_PROTECTION,
                "true"
        );

        createIfMissing(
                CAPACITY_PROTECTION,
                "true"
        );

        createIfMissing(
                SKILL_WEIGHT,
                "60"
        );

        createIfMissing(
                PROFICIENCY_WEIGHT,
                "40"
        );
    }

    private void createIfMissing(
            String key,
            String value) {

        if (settingRepository
                .findBySettingKey(key)
                .isEmpty()) {

            settingRepository.save(
                    SystemSetting.builder()
                            .settingKey(key)
                            .settingValue(value)
                            .build()
            );
        }
    }

    public boolean isApprovalRequired() {

        return getBoolean(
                APPROVAL_REQUIRED,
                true
        );
    }

    public boolean isDuplicateProtectionEnabled() {

        return getBoolean(
                DUPLICATE_PROTECTION,
                true
        );
    }

    public boolean isCapacityProtectionEnabled() {

        return getBoolean(
                CAPACITY_PROTECTION,
                true
        );
    }

    public double getSkillWeight() {

        return getDouble(
                SKILL_WEIGHT,
                60
        );
    }

    public double getProficiencyWeight() {

        return getDouble(
                PROFICIENCY_WEIGHT,
                40
        );
    }

    private boolean getBoolean(
            String key,
            boolean defaultValue) {

        return settingRepository
                .findBySettingKey(key)
                .map(setting ->
                        Boolean.parseBoolean(
                                setting.getSettingValue()))
                .orElse(defaultValue);
    }

    private double getDouble(
            String key,
            double defaultValue) {

        return settingRepository
                .findBySettingKey(key)
                .map(setting -> {

                    try {

                        return Double.parseDouble(
                                setting.getSettingValue());

                    } catch (NumberFormatException e) {

                        return defaultValue;
                    }

                })
                .orElse(defaultValue);
    }

    @Transactional
    public void updateSettings(
            boolean approvalRequired,
            boolean duplicateProtection,
            boolean capacityProtection,
            double skillWeight,
            double proficiencyWeight) {

        if (skillWeight < 0 ||
                proficiencyWeight < 0) {

            throw new IllegalArgumentException(
                    "Weights cannot be negative");
        }

        if (Math.abs(
                (skillWeight + proficiencyWeight) - 100
        ) > 0.001) {

            throw new IllegalArgumentException(
                    "Skill and proficiency weights must total 100%");
        }

        saveSetting(
                APPROVAL_REQUIRED,
                String.valueOf(approvalRequired)
        );

        saveSetting(
                DUPLICATE_PROTECTION,
                String.valueOf(duplicateProtection)
        );

        saveSetting(
                CAPACITY_PROTECTION,
                String.valueOf(capacityProtection)
        );

        saveSetting(
                SKILL_WEIGHT,
                String.valueOf(skillWeight)
        );

        saveSetting(
                PROFICIENCY_WEIGHT,
                String.valueOf(proficiencyWeight)
        );
    }

    private void saveSetting(
            String key,
            String value) {

        SystemSetting setting =
                settingRepository
                        .findBySettingKey(key)
                        .orElseGet(() ->
                                SystemSetting.builder()
                                        .settingKey(key)
                                        .build()
                        );

        setting.setSettingValue(value);

        settingRepository.save(setting);
    }

    @PostConstruct
    public void initialize() {
        initializeDefaults();
    }
}

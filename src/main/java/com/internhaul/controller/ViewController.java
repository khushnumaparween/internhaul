package com.internhaul.controller;

import com.internhaul.service.AllocationService;
import com.internhaul.service.DashboardService;
import com.internhaul.service.InternService;
import com.internhaul.service.ProjectService;
import com.internhaul.service.MatchingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {

    private final InternService internService;
    private final DashboardService dashboardService;
    private final ProjectService projectService;
    private final AllocationService allocationService;
    private final MatchingService matchingService;

    public ViewController(
            InternService internService,
            DashboardService dashboardService,
            ProjectService projectService,
            AllocationService allocationService,
            MatchingService matchingService) {

        this.internService = internService;
        this.dashboardService = dashboardService;
        this.projectService = projectService;
        this.allocationService = allocationService;
        this.matchingService = matchingService;
    }

    @GetMapping("/")
    public String dashboard(Model model) {

        addDashboardData(model);

        return "dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboardPage(Model model) {

        addDashboardData(model);

        return "dashboard";
    }

    private void addDashboardData(Model model) {

        model.addAttribute(
                "stats",
                dashboardService.getStats()
        );

        model.addAttribute(
                "interns",
                internService.getAllInterns()
        );

        model.addAttribute(
                "allocations",
                allocationService.getAllAllocations()
        );

        var currentProject =
                projectService.getCurrentProject();

        model.addAttribute(
                "currentProject",
                currentProject
        );

        /*
         * Smart Matching
         *
         * Find the best available interns
         * for the current active project.
         */

        if (currentProject != null) {

            model.addAttribute(
                    "matches",
                    matchingService.findBestMatches(
                            currentProject.getId()
                    )
            );
        }
    }

    @GetMapping("/interns")
    public String internsPage(
            @RequestParam(required = false) Boolean available,
            Model model) {

        if (Boolean.TRUE.equals(available)) {

            model.addAttribute(
                    "interns",
                    internService.getAvailableInterns()
            );

        } else {

            model.addAttribute(
                    "interns",
                    internService.getAllInterns()
            );
        }

        return "interns";
    }

    @GetMapping("/interns/{id}")
    public String internPage(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "intern",
                internService.getInternById(id)
        );

        return "intern";
    }

    @GetMapping("/projects")
    public String projectsPage(Model model) {

        model.addAttribute(
                "projects",
                projectService.getAllProjects()
        );

        return "projects";
    }

//    @GetMapping("/projects/{id}")
//    public String projectPage(
//            @PathVariable Long id,
//            Model model) {
//
//        model.addAttribute(
//                "project",
//                projectService.getProjectById(id)
//        );
//
//        return "project";
//    }

    @GetMapping("/projects/{id}")
    public String projectPage(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "project",
                projectService.getProjectById(id)
        );

        model.addAttribute(
                "allocations",
                allocationService.getAllocationsByProject(id)
        );

        return "project";
    }

    @GetMapping("/allocations")
    public String allocationsPage(Model model) {

        model.addAttribute(
                "allocations",
                allocationService.getAllAllocations()
        );

        return "allocations";
    }

    @GetMapping("/matching")
    public String matchingPage(Model model) {

        var currentProject =
                projectService.getCurrentProject();

        model.addAttribute(
                "currentProject",
                currentProject
        );

        if (currentProject != null) {

            model.addAttribute(
                    "matches",
                    matchingService.findBestMatches(
                            currentProject.getId()
                    )
            );
        }

        return "matching";
    }



    @GetMapping("/settings")
    public String settingsPage() {
        return "settings";
    }



}
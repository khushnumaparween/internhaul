package com.internhaul.controller;

import com.internhaul.dto.AllocationResponse;
import com.internhaul.entity.AllocationStatus;
import com.internhaul.service.AllocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/allocations")
public class AllocationController {

    private final AllocationService allocationService;

    public AllocationController(AllocationService allocationService) {
        this.allocationService = allocationService;
    }

    @PostMapping
    public ResponseEntity<AllocationResponse> createAllocation(
            @RequestParam Long internId,
            @RequestParam Long projectId) {

        AllocationResponse allocation =
                allocationService.createAllocation(
                        internId,
                        projectId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(allocation);
    }

    @GetMapping
    public ResponseEntity<List<AllocationResponse>> getAllAllocations() {

        return ResponseEntity.ok(
                allocationService.getAllAllocations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AllocationResponse> getAllocationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                allocationService.getAllocationById(id));
    }
//
//    @PatchMapping("/{id}/approve")
//    public ResponseEntity<Allocation> approveAllocation(
//            @PathVariable Long id) {
//
//        return ResponseEntity.ok(
//                allocationService.approveAllocation(id));
//    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<AllocationResponse> approveAllocation(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                allocationService.approveAllocation(id));
    }


    @PatchMapping("/{id}/reject")
    public ResponseEntity<AllocationResponse> rejectAllocation(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                allocationService.rejectAllocation(id));
    }


    @GetMapping("/status/{status}")
    public ResponseEntity<List<AllocationResponse>> getAllocationsByStatus(
            @PathVariable AllocationStatus status) {

        return ResponseEntity.ok(
                allocationService.getAllocationsByStatus(status));
    }


    @GetMapping("/intern/{internId}")
    public ResponseEntity<List<AllocationResponse>> getAllocationsByIntern(
            @PathVariable Long internId) {

        return ResponseEntity.ok(
                allocationService.getAllocationsByIntern(internId));
    }



    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<AllocationResponse>> getAllocationsByProject(
            @PathVariable Long projectId) {

        return ResponseEntity.ok(
                allocationService.getAllocationsByProject(projectId));
    }
}
package com.internhaul.controller;

import com.internhaul.entity.Intern;
import com.internhaul.service.InternService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/interns")
public class InternController {

    private final InternService internService;

    public InternController(InternService internService) {
        this.internService = internService;
    }

    @PostMapping
    public ResponseEntity<Intern> createIntern(
            @RequestBody Intern intern) {

        Intern savedIntern = internService.createIntern(intern);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedIntern);
    }

    @GetMapping
    public ResponseEntity<List<Intern>> getAllInterns() {

        return ResponseEntity.ok(
                internService.getAllInterns()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Intern> getInternById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                internService.getInternById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Intern> updateIntern(
            @PathVariable Long id,
            @RequestBody Intern intern) {

        return ResponseEntity.ok(
                internService.updateIntern(id, intern)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIntern(
            @PathVariable Long id) {

        internService.deleteIntern(id);

        return ResponseEntity.noContent().build();
    }
}

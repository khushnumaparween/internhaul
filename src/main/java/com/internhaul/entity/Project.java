package com.internhaul.entity;

import jakarta.persistence.*;
        import lombok.*;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    private String technology;

    private String location;

    @Column(nullable = false)
    private int availableSlots;

    @Column(nullable = false)
    private boolean active;
}
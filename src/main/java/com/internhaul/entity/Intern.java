package com.internhaul.entity;

import jakarta.persistence.*;
        import lombok.*;

@Entity
@Table(name = "interns")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Intern {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String location;

    @Column(nullable = false)
    private boolean available;
}

package com.internhaul.repository;

import com.internhaul.entity.Intern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternRepository extends JpaRepository<Intern, Long> {

    long countByAvailableTrue();

    List<Intern> findByAvailableTrue();
}
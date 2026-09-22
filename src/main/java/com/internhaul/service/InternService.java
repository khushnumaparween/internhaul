package com.internhaul.service;

import com.internhaul.entity.Intern;
import com.internhaul.repository.InternRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InternService {

    private final InternRepository internRepository;

    public InternService(InternRepository internRepository) {
        this.internRepository = internRepository;
    }

    public Intern createIntern(Intern intern) {
        return internRepository.save(intern);
    }

    public List<Intern> getAllInterns() {
        return internRepository.findAll();
    }

    public List<Intern> getAvailableInterns() {
        return internRepository.findByAvailableTrue();
    }

    public Intern getInternById(Long id) {
        return internRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Intern not found with id: " + id));
    }

    public Intern updateIntern(Long id, Intern updatedIntern) {

        Intern existingIntern = getInternById(id);

        existingIntern.setName(updatedIntern.getName());
        existingIntern.setEmail(updatedIntern.getEmail());
        existingIntern.setLocation(updatedIntern.getLocation());
        existingIntern.setAvailable(updatedIntern.isAvailable());

        return internRepository.save(existingIntern);
    }

    public void deleteIntern(Long id) {

        Intern intern = getInternById(id);

        internRepository.delete(intern);
    }
}

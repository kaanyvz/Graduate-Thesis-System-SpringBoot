package com.ky.dbmanagementsystem.repository;

import com.ky.dbmanagementsystem.model.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UniversityRepository extends JpaRepository<University, String> {
    boolean existsByNameIgnoreCase(String lowerCaseNameOfUniversity);

    Optional<University> findByName(String name);
}

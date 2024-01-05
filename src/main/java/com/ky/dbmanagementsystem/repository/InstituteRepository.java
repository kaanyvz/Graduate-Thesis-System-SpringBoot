package com.ky.dbmanagementsystem.repository;

import com.ky.dbmanagementsystem.model.Institute;
import com.ky.dbmanagementsystem.model.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InstituteRepository extends JpaRepository<Institute, Integer> {
    boolean existsByNameAndUniversity(String name, University university);

    Institute findByName(String instituteName);

    List<Institute> findByUniversity(University university);
    Optional<Institute> findByUniversityAndName(University university, String name);
}

package com.ky.dbmanagementsystem.repository;

import com.ky.dbmanagementsystem.model.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SupervisorRepository extends JpaRepository<Supervisor, Integer> {

    Supervisor findByNameAndLastName(String name, String lastname);
}

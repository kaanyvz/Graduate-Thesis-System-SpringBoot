package com.ky.dbmanagementsystem.repository;

import com.ky.dbmanagementsystem.model.CoSupervisor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoSupervisorRepository extends JpaRepository<CoSupervisor, Integer> {

    CoSupervisor findByNameAndLastname(String name, String lastname);

}

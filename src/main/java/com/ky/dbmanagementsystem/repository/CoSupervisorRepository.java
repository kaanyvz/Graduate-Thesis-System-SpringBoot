package com.ky.dbmanagementsystem.repository;

import com.ky.dbmanagementsystem.model.CoSupervisor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoSupervisorRepository extends JpaRepository<CoSupervisor, String> {
    boolean existsByMail(String mail);

//    CoSupervisor findByName(String coSupervisorName);
    CoSupervisor findByNameAndLastname(String name, String lastname);

}

package com.ky.dbmanagementsystem.repository;

import com.ky.dbmanagementsystem.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, String> {
    boolean existsByMail(String mail);

    Author findByNameAndLastName(String name, String lastname);

    Optional<Author> findByMail(String mail);



}

package com.ky.dbmanagementsystem.repository;

import com.ky.dbmanagementsystem.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface KeywordRepository extends JpaRepository<Keyword, String> {
    Optional<Keyword> findByName(String keywordName);
}

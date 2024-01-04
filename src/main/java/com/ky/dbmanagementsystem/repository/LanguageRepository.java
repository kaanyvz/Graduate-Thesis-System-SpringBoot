package com.ky.dbmanagementsystem.repository;

import com.ky.dbmanagementsystem.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, String> {
    boolean existsByName(String name);

    Language findByName(String languageName);
}

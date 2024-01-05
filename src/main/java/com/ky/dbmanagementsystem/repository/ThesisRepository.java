package com.ky.dbmanagementsystem.repository;

import com.ky.dbmanagementsystem.model.Author;
import com.ky.dbmanagementsystem.model.Institute;
import com.ky.dbmanagementsystem.model.Thesis;
import com.ky.dbmanagementsystem.model.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ThesisRepository extends JpaRepository<Thesis, Integer> {
    boolean existsThesisByIdAndSupervisorsId(Integer thesisId, Integer supervisorId);

    List<Thesis> findByAuthor(Author author);

    List<Thesis> findByUniversity(University university);

    List<Thesis> findByInstitute(Institute institute);

    @Query("SELECT thesis FROM Thesis thesis WHERE LOWER(thesis.thesis_title) = LOWER(:thesisTitle) ")
    List<Thesis> findByThesisTitle(@Param("thesisTitle") String thesis_title);

    @Query("SELECT COUNT(supervisors) FROM Thesis thesis JOIN thesis.supervisors supervisors WHERE thesis.id = :thesisId")
    int getCountOfSupervisorsInThesis(@Param("thesisId") Integer thesisId);

    Thesis findByThesisNo(Integer thesisNo);

    @Query("SELECT t FROM Thesis t " +
            "WHERE (:title IS NULL OR t.thesis_title LIKE %:title% ) " +
            "AND (:year IS NULL OR t.thesis_year = :year)" +
            "AND (:type IS NULL OR t.thesis_type = :type)" +
            "AND (:language IS NULL OR t.language.name = :language)" +
            "AND (:university IS NULL OR t.university.name = :university)" +
            "AND (:author IS NULL OR t.author.name = :author)" +
            "AND (:institute IS NULL OR t.institute.name = :institute)")
    List<Thesis> searchTheses(String title, Integer year, String type, String language, String university,
                              String author, String institute);

    List<Thesis> findByUniversityId(Integer id);
}

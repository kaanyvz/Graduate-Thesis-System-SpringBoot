package com.ky.dbmanagementsystem.repository;

import com.ky.dbmanagementsystem.model.SubjectTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SubjectTopicRepository extends JpaRepository<SubjectTopic, String> {

    List<SubjectTopic> findAllByNameIn(List<String> subjectTopicNames);
}

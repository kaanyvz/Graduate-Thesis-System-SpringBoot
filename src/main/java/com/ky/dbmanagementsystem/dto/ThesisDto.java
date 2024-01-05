package com.ky.dbmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThesisDto {
    private int id;
    private Integer thesisNo;
    private String thesis_title;
    private String thesis_abstract;
    private String thesis_type;
    private Integer numberOfPages;
    private LanguageDto language;
    private UniversityDto university;
    private InstituteDto institute;
    private AuthorDto author;
    private CoSupervisorDto coSupervisor;
    private LocalDateTime submissionDate;
    private LocalDateTime thesisYear;
    private List<KeywordDto> keywords;
    private List<SubjectTopicDto> subjectTopics;
    private List<SupervisorDto> supervisors;
}

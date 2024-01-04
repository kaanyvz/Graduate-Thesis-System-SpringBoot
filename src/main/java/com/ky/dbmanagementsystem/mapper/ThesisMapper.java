package com.ky.dbmanagementsystem.mapper;

import com.ky.dbmanagementsystem.dto.ThesisDto;
import com.ky.dbmanagementsystem.model.Thesis;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ThesisMapper {
    private final AuthorMapper authorMapper;
    private final LanguageMapper languageMapper;
    private final UniversityMapper universityMapper;
    private final KeywordMapper keywordMapper;
    private final SupervisorMapper supervisorMapper;
    private final CoSupervisorMapper coSupervisorMapper;
    private final InstituteMapper instituteMapper;
    private final SubjectTopicMapper subjectTopicMapper;

    public ThesisMapper(AuthorMapper authorMapper,
                        LanguageMapper languageMapper,
                        UniversityMapper universityMapper,
                        KeywordMapper keywordMapper,
                        SupervisorMapper supervisorMapper,
                        CoSupervisorMapper coSupervisorMapper,
                        InstituteMapper instituteMapper,
                        SubjectTopicMapper subjectTopicMapper) {
        this.authorMapper = authorMapper;
        this.languageMapper = languageMapper;
        this.universityMapper = universityMapper;
        this.keywordMapper = keywordMapper;
        this.supervisorMapper = supervisorMapper;
        this.coSupervisorMapper = coSupervisorMapper;
        this.instituteMapper = instituteMapper;
        this.subjectTopicMapper = subjectTopicMapper;
    }

    public ThesisDto thesisToThesisDto(Thesis thesis){
        return ThesisDto.builder()
                .id(thesis.getId())
                .thesisNo(thesis.getThesisNo())
                .thesis_abstract(thesis.getThesis_abstract())
                .thesis_type(thesis.getThesis_type())
                .thesis_title(thesis.getThesis_title())
                .numberOfPages(thesis.getNumberOfPages())
                .author(authorMapper.authorToAuthorDto(thesis.getAuthor()))
                .institute(instituteMapper.instituteToInstituteDto(thesis.getInstitute()))
                .language(languageMapper.languageToLanguageDto(thesis.getLanguage()))
                .university(universityMapper.universityToUniversityDto(thesis.getUniversity()))
                .keywords(thesis.getKeywords().stream().map(keywordMapper::keywordToKeywordDto)
                        .collect(Collectors.toList()))
                .subjectTopics(thesis.getSubjectTopics().stream().map(subjectTopicMapper::subjectTopicToSubjectTopicDto)
                        .collect(Collectors.toList()))
                .supervisors(thesis.getSupervisors().stream().map(supervisorMapper::supervisorToSupervisorDto)
                        .collect(Collectors.toList()))
                .coSupervisor(coSupervisorMapper.coSupervisorToCoSupervisorDto(thesis.getCoSupervisor()))
                .submissionDate(thesis.getSubmissionDate())
                .thesisYear(thesis.getThesis_year())
                .build();

    }
}

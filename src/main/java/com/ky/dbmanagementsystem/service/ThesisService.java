package com.ky.dbmanagementsystem.service;

import com.ky.dbmanagementsystem.dto.ThesisDto;
import com.ky.dbmanagementsystem.exception.*;
import com.ky.dbmanagementsystem.exception.Duplication.DuplicateSubjectTopicException;
import com.ky.dbmanagementsystem.exception.Duplication.DuplicateSupervisorInThesisException;
import com.ky.dbmanagementsystem.exception.NotFound.ThesisNotFoundException;
import com.ky.dbmanagementsystem.mapper.ThesisMapper;
import com.ky.dbmanagementsystem.model.*;
import com.ky.dbmanagementsystem.repository.AuthorRepository;
import com.ky.dbmanagementsystem.repository.KeywordRepository;
import com.ky.dbmanagementsystem.repository.SubjectTopicRepository;
import com.ky.dbmanagementsystem.repository.ThesisRepository;
import com.ky.dbmanagementsystem.request.add.AddKeywordsToThesisRequest;
import com.ky.dbmanagementsystem.request.add.AddSubjectTopicsToThesisRequest;
import com.ky.dbmanagementsystem.request.add.AddSupervisorToThesisRequest;
import com.ky.dbmanagementsystem.request.create.CreateThesisRequest;
import com.ky.dbmanagementsystem.request.update.UpdateSupervisorRequest;
import com.ky.dbmanagementsystem.request.update.UpdateThesisRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ThesisService {
    private final ThesisRepository thesisRepository;
    private final LanguageService languageService;
    private final AuthorService authorService;
    private final UniversityService universityService;
    private final InstituteService instituteService;
    private final CoSupervisorService coSupervisorService;
    private final ThesisMapper thesisMapper;
    private final SupervisorService supervisorService;
    private final KeywordRepository keywordRepository;
    private final SubjectTopicRepository subjectTopicRepository;
    private final AuthorRepository authorRepository;


    public ThesisService(ThesisRepository thesisRepository,
                         LanguageService languageService,
                         AuthorService authorService,
                         UniversityService universityService,
                         InstituteService instituteService,
                         CoSupervisorService coSupervisorService,
                         ThesisMapper thesisMapper,
                         SupervisorService supervisorService, KeywordRepository keywordRepository, SubjectTopicRepository subjectTopicRepository, AuthorRepository authorRepository)
    {
        this.thesisRepository = thesisRepository;
        this.languageService = languageService;
        this.authorService = authorService;
        this.universityService = universityService;
        this.instituteService = instituteService;
        this.coSupervisorService = coSupervisorService;
        this.thesisMapper = thesisMapper;
        this.supervisorService = supervisorService;
        this.keywordRepository = keywordRepository;
        this.subjectTopicRepository = subjectTopicRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public ThesisDto createThesis(CreateThesisRequest createThesisRequest){
        Language language = languageService.getLanguageByName(createThesisRequest.getLanguageName());
        Author author = authorService.getAuthorByName(createThesisRequest.getAuthorName(), createThesisRequest.getAuthorLastname());
        University university = universityService.getUniversityByName(createThesisRequest.getUniversityName());
        Institute institute = instituteService.getInstituteByNameAndUniversity(createThesisRequest.getInstituteName(), university);
        CoSupervisor coSupervisor = coSupervisorService.getOrCreateCoSupervisor(createThesisRequest.getCoSupervisorName(), createThesisRequest.getCoSupervisorLastname());

        System.out.println(coSupervisor);
        Thesis thesis = Thesis.builder()
                .thesisNo(createThesisRequest.getThesisNo())
                .thesis_title(createThesisRequest.getThesis_title())
                .thesis_abstract(createThesisRequest.getThesis_abstract())
                .thesis_type(createThesisRequest.getThesis_type())
                .numberOfPages(createThesisRequest.getNumberOfPages())
                .language(language)
                .university(university)
                .institute(institute)
                .author(author)
                .coSupervisor(coSupervisor)
                .keywords(new ArrayList<>())
                .subjectTopics(new ArrayList<>())
                .supervisors(new ArrayList<>())
                .submissionDate(LocalDateTime.now())
                .thesis_year(createThesisRequest.getThesisYear())
                .build();

        Thesis savedThesis = thesisRepository.save(thesis);

        return thesisMapper.thesisToThesisDto(savedThesis);
    }

    public ThesisDto getThesisById(int id){
        Thesis thesis = thesisRepository.findById(id)
                .orElseThrow(() -> new ThesisNotFoundException("Thesis could not found by id."));
        return thesisMapper.thesisToThesisDto(thesis);
    }

    public List<ThesisDto> getAllTheses(){
        return thesisRepository.findAll()
                .stream()
                .map(thesisMapper::thesisToThesisDto)
                .collect(Collectors.toList());
    }


    //ADD
    public ThesisDto addSupervisorToThesis(AddSupervisorToThesisRequest request){

        Supervisor supervisor = supervisorService.addSupervisor(request.getSupervisorName(), request.getSupervisorLastname());
        Thesis thesis = getThesisByThesisNo(request.getThesisNo());

        boolean exists = thesisRepository.existsThesisByIdAndSupervisorsId(thesis.getId(), supervisor.getId());

        if (!exists) {
            thesis.getSupervisors().add(supervisor);
            Thesis savedThesis = thesisRepository.save(thesis);
            return thesisMapper.thesisToThesisDto(savedThesis);
        } else {
            throw new DuplicateSupervisorInThesisException("Supervisor is already associated with the thesis");
        }
    }

    public ThesisDto updateSupervisorsInThesis(UpdateSupervisorRequest request){
        Supervisor newSupervisor = supervisorService.addSupervisor(request.getName(), request.getLastname());
        Thesis thesis = thesisRepository.findByThesisNo(request.getThesisNo());

        boolean isSupervisorExists = thesis.getSupervisors().stream()
                .anyMatch(supervisor -> supervisor.getName().equals(request.getName()) && supervisor.getLastName().equals(request.getLastname()));

        if(!isSupervisorExists){
            thesis.getSupervisors().clear();
            thesis.getSupervisors().add(newSupervisor);

            Thesis savedThesis = thesisRepository.save(thesis);
            return thesisMapper.thesisToThesisDto(savedThesis);
        }else{
            throw new DuplicateSupervisorInThesisException("Supervisor already exists in this thesis");
        }
    }

    public String addSubjectTopicsToThesis(AddSubjectTopicsToThesisRequest request) {
        Thesis thesis = thesisRepository.findByThesisNo(request.getThesisNo());

        List<String> normalizedSubjectTopicNames = request.getSubjectTopicNames().stream()
                .map(String::trim)
                .map(String::toLowerCase)
                .toList();

        List<SubjectTopic> subjectTopicsToAdd = subjectTopicRepository.findAllByNameIn(normalizedSubjectTopicNames);

        if(subjectTopicsToAdd.isEmpty()){
            throw new NonValidSubjectTopicException("None of the specified subject topics were found in the database.");
        }

        thesis.getSubjectTopics().clear();

        for (SubjectTopic subjectTopic : subjectTopicsToAdd) {
            boolean alreadyAssociated = thesis.getSubjectTopics().stream()
                    .anyMatch(existingTopic -> existingTopic.getName().equalsIgnoreCase(subjectTopic.getName()));

            if (alreadyAssociated) {
                throw new DuplicateSubjectTopicException("Subject topic '" + subjectTopic.getName() +
                        "' is already associated with the thesis");
            } else {
                thesis.getSubjectTopics().add(subjectTopic);
            }
        }

        thesisRepository.save(thesis);
        return "topics added successfully..";
    }

    public String addKeywordsToThesis(AddKeywordsToThesisRequest addKeywordsToThesis){
        Thesis thesis = getThesisByThesisNo(addKeywordsToThesis.getThesisNo());

        List<Keyword> keywords = createKeywords(addKeywordsToThesis.getKeywordNames());

        for(Keyword keyword : keywords){
            if(!thesis.getKeywords().contains(keyword)){
                thesis.getKeywords().add(keyword);
            }
        }

        thesisRepository.save(thesis);

        return "Keywords added successfully.";
    }

    public String updateKeywordsInThesis(AddKeywordsToThesisRequest addKeywordsToThesis){
        Thesis thesis = getThesisByThesisNo(addKeywordsToThesis.getThesisNo());

        thesis.getKeywords().clear();

        List<Keyword> keywords = createKeywords(addKeywordsToThesis.getKeywordNames());

        thesis.getKeywords().addAll(keywords);

        thesisRepository.save(thesis);

        return "Keywords added successfully.";
    }



    //GET
    public List<ThesisDto> getThesesByAuthorName(String name, String lastname){
        Author author = authorService.getAuthorByName(name, lastname);

        List<Thesis> theses = thesisRepository.findByAuthor(author);

        return theses.stream()
                .map(thesisMapper::thesisToThesisDto)
                .collect(Collectors.toList());
    }


    public List<ThesisDto> getThesesByUniversityName(String name){
        University university = universityService.getUniversityByName(name);

        List<Thesis> theses = thesisRepository.findByUniversity(university);

        return theses.stream()
                .map(thesisMapper::thesisToThesisDto)
                .collect(Collectors.toList());
    }

    public List<ThesisDto> getThesisByTitle(String title){
        List<Thesis> theses = thesisRepository.findByThesisTitle(title);

        return theses.stream()
                .map(thesisMapper::thesisToThesisDto)
                .collect(Collectors.toList());
    }

    public Integer getCountOfSupervisorsInThesis(int thesisId){
        return thesisRepository.getCountOfSupervisorsInThesis(thesisId);
    }

    public List<ThesisDto> getThesesByInstituteName(String instituteName){
        Institute institute = instituteService.getInstituteByName(instituteName);

        List<Thesis> theses = thesisRepository.findByInstitute(institute);

        return theses.stream()
                .map(thesisMapper::thesisToThesisDto)
                .collect(Collectors.toList());
    }

    public List<ThesisDto> getThesisByMail(String mail){
        Author author = authorRepository.findByMail(mail)
                .orElseThrow(() -> new RuntimeException("Author could not found by mail: " + mail));

        List<Thesis> theses = thesisRepository.findByAuthor(author);
        return theses.stream()
                .map(thesisMapper::thesisToThesisDto)
                .collect(Collectors.toList());
    }

    public ThesisDto fetchThesisByThesisNo(Integer thesisNo){
        Thesis thesis = thesisRepository.findByThesisNo(thesisNo);
        return thesisMapper.thesisToThesisDto(thesis);
    }

    //update methodds
    public ThesisDto updateThesis(int thesisId, UpdateThesisRequest request){
        Author author = authorService.getAuthorByName(request.getAuthorName(), request.getAuthorLastname());
        Language language = languageService.getLanguageByName(request.getLanguageName());
        University university = universityService.getUniversityByName(request.getUniversityName());
        Institute institute = instituteService.getInstituteByNameAndUniversity(request.getInstituteName(), university);
        CoSupervisor coSupervisor = coSupervisorService.getCoSupervisorByName(request.getCoSupervisorName(), request.getCoSupervisorLastname());

        Thesis thesis = getThesis(thesisId);

        thesis.setThesisNo(request.getThesisNo());
        thesis.setThesis_title(request.getThesis_title());
        thesis.setThesis_type(request.getThesis_type());
        thesis.setThesis_abstract(request.getThesis_abstract());
        thesis.setNumberOfPages(request.getNumberOfPages());
        thesis.setLanguage(language);
        thesis.setAuthor(author);
        thesis.setUniversity(university);
        thesis.setInstitute(institute);
        thesis.setCoSupervisor(coSupervisor);

        Thesis savedThesis = thesisRepository.save(thesis);

        return thesisMapper.thesisToThesisDto(savedThesis);
    }
    public List<ThesisDto> searchTheses(String title,
                                    String year,
                                    String type,
                                    String language,
                                    String university,
                                    String author,
                                    String institute
                                    ){
        Integer yearInt = null;
        if (year != null && !year.isEmpty()) {
            try {
                yearInt = Integer.parseInt(year.split("-")[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        List<Thesis> searchedThesis =  thesisRepository.searchTheses(title, yearInt, type, language, university, author, institute);
        return searchedThesis.stream()
                .map(thesisMapper::thesisToThesisDto)
                .collect(Collectors.toList());
    }

    public String deleteById(int id){
        Thesis thesis = thesisRepository.findById(id)
                .orElseThrow(() -> new ThesisNotFoundException("Thesis could not found by id: "));
        thesisRepository.delete(thesis);
        return "Your Thesis has successfully deleted from the system.";
    }


    //PRIVATE
    private Thesis getThesis(int id){
        return thesisRepository.findById(id)
                .orElseThrow(() -> new ThesisNotFoundException("Thesis could not found by id."));
    }

    private List<Keyword> createKeywords(List<String> keywordNames){

        List<Keyword> keywords = new ArrayList<>();
        for(String keywordName : keywordNames){
            Optional<Keyword> existingKeyword = keywordRepository.findByName(keywordName);
            if(existingKeyword.isPresent()){
                keywords.add(existingKeyword.get());
            }else{
                Keyword newKeyword = new Keyword(keywordName);
                keywords.add(keywordRepository.save(newKeyword));
            }
        }
        return keywords;
    }

    private Thesis getThesisByThesisNo(Integer thesisNo){
        Thesis thesis = thesisRepository.findByThesisNo(thesisNo);
        if(thesis == null){
            throw new ThesisNotFoundException("Thesis could not found by thesisNo " + thesisNo);
        }
        return thesis;
    }



}

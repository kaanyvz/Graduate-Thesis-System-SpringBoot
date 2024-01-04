package com.ky.dbmanagementsystem.service;

import com.ky.dbmanagementsystem.dto.UniversityDto;
import com.ky.dbmanagementsystem.exception.Duplication.DuplicateUniversityException;
import com.ky.dbmanagementsystem.exception.NotFound.UniversityNotFoundException;
import com.ky.dbmanagementsystem.mapper.UniversityMapper;
import com.ky.dbmanagementsystem.model.Institute;
import com.ky.dbmanagementsystem.model.Thesis;
import com.ky.dbmanagementsystem.model.University;
import com.ky.dbmanagementsystem.repository.InstituteRepository;
import com.ky.dbmanagementsystem.repository.ThesisRepository;
import com.ky.dbmanagementsystem.repository.UniversityRepository;
import com.ky.dbmanagementsystem.request.create.CreateUniversityRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UniversityService {
    private final UniversityRepository universityRepository;
    private final UniversityMapper mapper;
    private final InstituteRepository instituteRepository;
    private final ThesisRepository thesisRepository;

    @Autowired
    public UniversityService(UniversityRepository universityRepository, UniversityMapper mapper, InstituteRepository instituteRepository, ThesisRepository thesisRepository) {
        this.universityRepository = universityRepository;
        this.mapper = mapper;
        this.instituteRepository = instituteRepository;
        this.thesisRepository = thesisRepository;
    }

    public UniversityDto createUniversity(CreateUniversityRequest request){

        University university = University.builder()
                .name(request.getName())
                .institutes(new ArrayList<>())
                .build();

        String lowerCaseNameOfUniversity = university.getName().toLowerCase();

        if(universityRepository.existsByNameIgnoreCase(lowerCaseNameOfUniversity)){
            throw new DuplicateUniversityException("University " + lowerCaseNameOfUniversity + " already exists in the system.");
        }

        University savedUniversity = universityRepository.save(university);
        return mapper.universityToUniversityDto(savedUniversity);
    }

    public UniversityDto getUniversityById(String id){
        University university = universityRepository.findById(id)
                .orElseThrow(() -> new UniversityNotFoundException("University could not found by id!"));

        return mapper.universityToUniversityDto(university);
    }

    public University getUniversity(String id){
        return universityRepository.findById(id)
                .orElseThrow(() -> new UniversityNotFoundException("University could not found by id!"));
    }

    public UniversityDto findUniversityByName(String name){
        University university = universityRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("University could not found by name: " + name));
        return mapper.universityToUniversityDto(university);
    }

    public String deleteUniversityById(String id){
        University university = universityRepository.findById(id)
                .orElseThrow(() -> new UniversityNotFoundException("University could not found by id! "));
        List<Institute> associatedInstitutes = university.getInstitutes();
        List<Thesis> theses = thesisRepository.findByUniversityId(id);
        instituteRepository.deleteAll(associatedInstitutes);
        thesisRepository.deleteAll(theses);
        universityRepository.delete(university);
        return ("University " + university.getName() + " has been deleted successfully...");
    }

    public List<UniversityDto> getAllUniversities(){
        return universityRepository.findAll()
                .stream()
                .map(mapper::universityToUniversityDto)
                .collect(Collectors.toList());
    }

    public University getUniversityByName(String name) {
        University university = universityRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("University could not found by name: " + name));
        if(university == null){
            throw new UniversityNotFoundException("University could not found: " + name);
        }
        return university;
    }
}

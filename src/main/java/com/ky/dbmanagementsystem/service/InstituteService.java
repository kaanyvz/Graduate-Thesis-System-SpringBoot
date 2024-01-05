package com.ky.dbmanagementsystem.service;

import com.ky.dbmanagementsystem.dto.InstituteDto;
import com.ky.dbmanagementsystem.dto.UniversityDto;
import com.ky.dbmanagementsystem.exception.NotFound.InstituteNotFoundException;
import com.ky.dbmanagementsystem.mapper.InstituteMapper;
import com.ky.dbmanagementsystem.model.Institute;
import com.ky.dbmanagementsystem.model.University;
import com.ky.dbmanagementsystem.repository.InstituteRepository;
import com.ky.dbmanagementsystem.request.create.CreateInstituteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstituteService {
    private final InstituteRepository instituteRepository;
    private final InstituteMapper instituteMapper;
    private final UniversityService universityService;

    public InstituteService(InstituteRepository instituteRepository, InstituteMapper instituteMapper, UniversityService universityService) {
        this.instituteRepository = instituteRepository;
        this.instituteMapper = instituteMapper;
        this.universityService = universityService;
    }

    public InstituteDto createInstitute(CreateInstituteRequest request){
        University university = universityService.getUniversityByName(request.getUniversityName());

        Institute institute = Institute.builder()
                .name(request.getName())
                .university(university)
                .build();

        if(instituteRepository.existsByNameAndUniversity(institute.getName(), university)){
            throw new RuntimeException(university.getName() + " has already " + institute.getName() + ".");
        }

        Institute savedInstitute = instituteRepository.save(institute);
        return instituteMapper.instituteToInstituteDto(savedInstitute);
    }

    public InstituteDto getInstituteById(int id){
        Institute institute = instituteRepository.findById(id)
                .orElseThrow(() -> new InstituteNotFoundException("Institute could not found by id "));
        return instituteMapper.instituteToInstituteDto(institute);
    }

    public Institute getInstitute(int id){
        return instituteRepository.findById(id)
                .orElseThrow(() -> new InstituteNotFoundException("Institute does not exist..."));
    }

    public String deleteInstituteById(int id){
        Institute institute = instituteRepository.findById(id)
                .orElseThrow(() -> new InstituteNotFoundException("Institute could not found by id! "));
        instituteRepository.delete(institute);
        return ("Institute " + institute.getName() + " has been deleted successfully...");
    }

    public List<InstituteDto> getAllInstitutes(){
        return instituteRepository.findAll()
                .stream()
                .map(instituteMapper::instituteToInstituteDto)
                .collect(Collectors.toList());
    }

    public List<InstituteDto> getInstitutesByUniversityName(String universityName) {
        University university = universityService.getUniversityByName(universityName);

        List<Institute> institutes = instituteRepository.findByUniversity(university);

        return institutes.stream()
                .map(instituteMapper::instituteToInstituteDto)
                .collect(Collectors.toList());
    }

    public Institute getInstituteByName(String instituteName) {
        Institute institute = instituteRepository.findByName(instituteName);
        if(institute == null){
            throw  new InstituteNotFoundException("Institute not found with name: " + instituteName);
        }
        return institute;
    }

    public Institute getInstituteByNameAndUniversity(String instituteName, University university) {
        return instituteRepository.findByUniversityAndName(university, instituteName)
                .orElseThrow(() -> new RuntimeException("Institute not found by name " + instituteName +
                        " and university: " + university.getName()));
    }
}

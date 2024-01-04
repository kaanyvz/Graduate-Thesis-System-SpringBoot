package com.ky.dbmanagementsystem.mapper;

import com.ky.dbmanagementsystem.dto.UniversityDto;
import com.ky.dbmanagementsystem.model.University;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UniversityMapper {
    private final InstituteMapper instituteMapper;

    public UniversityMapper(InstituteMapper instituteMapper) {
        this.instituteMapper = instituteMapper;
    }

    public UniversityDto universityToUniversityDto(University university){
        return UniversityDto.builder()
                .id(university.getId())
                .name(university.getName())
                .institutes(university.getInstitutes().stream().map(instituteMapper::instituteToInstituteDto).collect(Collectors.toList()))
                .build();
    }
}

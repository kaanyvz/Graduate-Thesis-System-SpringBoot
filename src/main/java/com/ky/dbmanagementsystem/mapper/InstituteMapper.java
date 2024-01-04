package com.ky.dbmanagementsystem.mapper;

import com.ky.dbmanagementsystem.dto.InstituteDto;
import com.ky.dbmanagementsystem.model.Institute;
import org.springframework.stereotype.Component;

@Component
public class InstituteMapper {

    public InstituteDto instituteToInstituteDto(Institute institute){
        return InstituteDto.builder()
                .id(institute.getId())
                .name(institute.getName())
                .build();
    }
}

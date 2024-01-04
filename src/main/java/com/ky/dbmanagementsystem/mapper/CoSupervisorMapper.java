package com.ky.dbmanagementsystem.mapper;

import com.ky.dbmanagementsystem.dto.CoSupervisorDto;
import com.ky.dbmanagementsystem.model.CoSupervisor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CoSupervisorMapper {


    public CoSupervisorDto coSupervisorToCoSupervisorDto(CoSupervisor coSupervisor){
        return CoSupervisorDto.builder()
                .id(coSupervisor.getId())
                .name(coSupervisor.getName())
                .lastname(coSupervisor.getLastname())
                .mail(coSupervisor.getMail())
                .build();
    }
}
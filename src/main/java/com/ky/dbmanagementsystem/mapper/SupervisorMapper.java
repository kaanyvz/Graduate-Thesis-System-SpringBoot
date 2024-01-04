package com.ky.dbmanagementsystem.mapper;

import com.ky.dbmanagementsystem.dto.SupervisorDto;
import com.ky.dbmanagementsystem.model.Supervisor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SupervisorMapper {


    public SupervisorDto supervisorToSupervisorDto(Supervisor supervisor){
        return SupervisorDto.builder()
                .id(supervisor.getId())
                .name(supervisor.getName())
                .lastname(supervisor.getLastName())
                .build();
    }
}

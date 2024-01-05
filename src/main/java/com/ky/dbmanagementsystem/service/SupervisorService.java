package com.ky.dbmanagementsystem.service;

import com.ky.dbmanagementsystem.dto.SupervisorDto;
import com.ky.dbmanagementsystem.exception.Duplication.DuplicateEmailException;
import com.ky.dbmanagementsystem.exception.NotFound.AuthorNotFoundException;
import com.ky.dbmanagementsystem.exception.NotFound.SupervisorNotFoundException;
import com.ky.dbmanagementsystem.mapper.SupervisorMapper;
import com.ky.dbmanagementsystem.model.Author;
import com.ky.dbmanagementsystem.model.SubjectTopic;
import com.ky.dbmanagementsystem.model.Supervisor;
import com.ky.dbmanagementsystem.repository.SupervisorRepository;
import com.ky.dbmanagementsystem.request.create.CreateSupervisorRequest;
import com.ky.dbmanagementsystem.request.update.UpdateSupervisorRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SupervisorService {
    private final SupervisorRepository supervisorRepository;
    private final SupervisorMapper supervisorMapper;

    public SupervisorService(SupervisorRepository supervisorRepository, SupervisorMapper supervisorMapper) {
        this.supervisorRepository = supervisorRepository;
        this.supervisorMapper = supervisorMapper;
    }

    public SupervisorDto createSupervisor(CreateSupervisorRequest createSupervisorRequest){
        Supervisor supervisor = Supervisor.builder()
                .name(createSupervisorRequest.getName())
                .lastName(createSupervisorRequest.getLastname())
                .build();


        Supervisor savedSupervisor = supervisorRepository.save(supervisor);
        return supervisorMapper.supervisorToSupervisorDto(savedSupervisor);
    }

    public SupervisorDto getSupervisorById(int id){
        Supervisor supervisor = supervisorRepository.findById(id)
                .orElseThrow(() -> new SupervisorNotFoundException("Supervisor could not found by id."));

        return supervisorMapper.supervisorToSupervisorDto(supervisor);
    }

    public Supervisor getSupervisor(int id){
        return supervisorRepository.findById(id)
                .orElseThrow(() -> new SupervisorNotFoundException("Supervisor does not exist..."));
    }

    public SupervisorDto updateSupervisor(UpdateSupervisorRequest request, int id){
        Supervisor supervisor = supervisorRepository.findById(id)
                .orElseThrow(() -> new SupervisorNotFoundException("Supervisor cannot find..."));

        supervisor.setName(request.getName());
        supervisor.setLastName(request.getLastname());

        Supervisor savedSupervisor = supervisorRepository.save(supervisor);
        return supervisorMapper.supervisorToSupervisorDto(savedSupervisor);

    }

    public Supervisor addSupervisor(String name, String lastname) {
//        Supervisor existingSupervisor = supervisorRepository.findByNameAndLastName(name, lastname);
//
//        if (existingSupervisor != null) {
//            throw new RuntimeException("Supervisor already exists");
//        }

        Supervisor newSupervisor = new Supervisor();
        newSupervisor.setName(name);
        newSupervisor.setLastName(lastname);

        return supervisorRepository.save(newSupervisor);
    }

    public Supervisor getAuthorByName(String name, String lastname) {
        Supervisor supervisor = supervisorRepository.findByNameAndLastName(name, lastname);
        if(supervisor == null){
            throw new AuthorNotFoundException("NULL!");
        }
        return supervisor;
    }

    public String deleteSupervisor(int id){
        Supervisor supervisor = supervisorRepository.findById(id)
                .orElseThrow(() -> new SupervisorNotFoundException("Supervisor could not found by id."));
        supervisorRepository.delete(supervisor);

        return "Supervisor " + "'"+ supervisor.getName() + supervisor.getLastName() +"'"+ " successfully deleted...";
     }

}

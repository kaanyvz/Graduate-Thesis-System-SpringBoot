package com.ky.dbmanagementsystem.service;

import com.ky.dbmanagementsystem.dto.CoSupervisorDto;
import com.ky.dbmanagementsystem.exception.Duplication.DuplicateEmailException;
import com.ky.dbmanagementsystem.exception.NotFound.CoSupervisorNotFoundException;
import com.ky.dbmanagementsystem.mapper.CoSupervisorMapper;
import com.ky.dbmanagementsystem.model.CoSupervisor;
import com.ky.dbmanagementsystem.repository.CoSupervisorRepository;
import com.ky.dbmanagementsystem.request.create.CreateCoSupervisorRequest;
import com.ky.dbmanagementsystem.request.update.UpdateCoSupervisorRequest;
import org.springframework.stereotype.Service;


@Service
public class CoSupervisorService {
    private final CoSupervisorRepository coSupervisorRepository;
    private final CoSupervisorMapper coSupervisorMapper;

    public CoSupervisorService(CoSupervisorRepository coSupervisorRepository, CoSupervisorMapper coSupervisorMapper) {
        this.coSupervisorRepository = coSupervisorRepository;
        this.coSupervisorMapper = coSupervisorMapper;
    }

    public CoSupervisorDto createCoSupervisor(CreateCoSupervisorRequest createCoSupervisorRequest){
        CoSupervisor coSupervisor = CoSupervisor.builder()
                .name(createCoSupervisorRequest.getName())
                .lastname(createCoSupervisorRequest.getLastname())
                .build();

        CoSupervisor savedCoSupervisor = coSupervisorRepository.save(coSupervisor);
        return coSupervisorMapper.coSupervisorToCoSupervisorDto(savedCoSupervisor);
    }

    public CoSupervisorDto getCoSupervisorById(int id){
        CoSupervisor coSupervisor = coSupervisorRepository.findById(id)
                .orElseThrow(() -> new CoSupervisorNotFoundException("CoSupervisor could not found by id."));

            return coSupervisorMapper.coSupervisorToCoSupervisorDto(coSupervisor);
    }

    public CoSupervisor getCoSupervisor(int id){
        return coSupervisorRepository.findById(id)
                .orElseThrow(() -> new CoSupervisorNotFoundException("CoSupervisor could not found by id."));
    }

    public CoSupervisorDto updateCoSupervisor(UpdateCoSupervisorRequest request, int id){
        CoSupervisor coSupervisor = coSupervisorRepository.findById(id)
                .orElseThrow(() -> new CoSupervisorNotFoundException("CoSupervisor could not found by id."));


        coSupervisor.setName(request.getName());
        coSupervisor.setLastname(request.getLastname());

        CoSupervisor savedCoSupervisor = coSupervisorRepository.save(coSupervisor);
        return coSupervisorMapper.coSupervisorToCoSupervisorDto(savedCoSupervisor);

    }

    public String deleteSupervisor(int id){
        CoSupervisor coSupervisor = coSupervisorRepository.findById(id)
                .orElseThrow(() -> new CoSupervisorNotFoundException("CoSupervisor could not found by id."));
        coSupervisorRepository.delete(coSupervisor);

        return "CoSupervisor " + "'"+ coSupervisor.getName() + coSupervisor.getLastname() +"'"+ " successfully deleted...";
    }


//    public CoSupervisor getCoSupervisorByName(String coSupervisorName) {
//        return coSupervisorRepository.findByName(coSupervisorName);
//    }

    public CoSupervisor getCoSupervisorByName(String name, String lastname){
        CoSupervisor coSupervisor = coSupervisorRepository.findByNameAndLastname(name, lastname);
        if(coSupervisor == null){
            throw new CoSupervisorNotFoundException("NULL COSUPERVISOR");
        }
        return coSupervisor;
    }

    public CoSupervisor getOrCreateCoSupervisor(String name, String lastname){
        CoSupervisor coSupervisor = coSupervisorRepository.findByNameAndLastname(name, lastname);
        if(coSupervisor == null){
            CoSupervisor newCoSupervisor = CoSupervisor.builder()
                    .name(name)
                    .lastname(lastname)
                    .build();
            return coSupervisorRepository.save(newCoSupervisor);
        }
        return coSupervisor;
    }
}
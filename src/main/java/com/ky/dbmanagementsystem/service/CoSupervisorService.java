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
                .mail(createCoSupervisorRequest.getMail())
                .build();

        if(checkMailAlreadyExists(createCoSupervisorRequest.getMail())){
            throw new DuplicateEmailException("This mail has already used before... Try to use another mail");
        }

        CoSupervisor savedCoSupervisor = coSupervisorRepository.save(coSupervisor);
        return coSupervisorMapper.coSupervisorToCoSupervisorDto(savedCoSupervisor);
    }

    public CoSupervisorDto getCoSupervisorById(String id){
        CoSupervisor coSupervisor = coSupervisorRepository.findById(id)
                .orElseThrow(() -> new CoSupervisorNotFoundException("CoSupervisor could not found by id."));

            return coSupervisorMapper.coSupervisorToCoSupervisorDto(coSupervisor);
    }

    public CoSupervisor getCoSupervisor(String id){
        return coSupervisorRepository.findById(id)
                .orElseThrow(() -> new CoSupervisorNotFoundException("CoSupervisor could not found by id."));
    }

    public CoSupervisorDto updateCoSupervisor(UpdateCoSupervisorRequest request, String id){
        CoSupervisor coSupervisor = coSupervisorRepository.findById(id)
                .orElseThrow(() -> new CoSupervisorNotFoundException("CoSupervisor could not found by id."));

        if (checkMailAlreadyExists(request.getMail())) {
            throw new DuplicateEmailException("Cannot update CoSupervisor: Email already exist");
        }

        coSupervisor.setName(request.getName());
        coSupervisor.setLastname(request.getLastname());
        coSupervisor.setMail(request.getMail());

        CoSupervisor savedCoSupervisor = coSupervisorRepository.save(coSupervisor);
        return coSupervisorMapper.coSupervisorToCoSupervisorDto(savedCoSupervisor);

    }

    public String deleteSupervisor(String id){
        CoSupervisor coSupervisor = coSupervisorRepository.findById(id)
                .orElseThrow(() -> new CoSupervisorNotFoundException("CoSupervisor could not found by id."));
        coSupervisorRepository.delete(coSupervisor);

        return "CoSupervisor " + "'"+ coSupervisor.getName() + coSupervisor.getLastname() +"'"+ " successfully deleted...";
    }

    private boolean checkMailAlreadyExists(String mail){
        return coSupervisorRepository.existsByMail(mail);
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
}
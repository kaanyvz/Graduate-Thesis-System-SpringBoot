package com.ky.dbmanagementsystem.controller;

import com.ky.dbmanagementsystem.dto.SupervisorDto;
import com.ky.dbmanagementsystem.request.create.CreateSupervisorRequest;
import com.ky.dbmanagementsystem.request.update.UpdateSupervisorRequest;
import com.ky.dbmanagementsystem.service.SupervisorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/supervisor")
public class SupervisorController {
    private final SupervisorService supervisorService;

    public SupervisorController(SupervisorService supervisorService) {
        this.supervisorService = supervisorService;
    }

    @PostMapping("/createSupervisor")
    public ResponseEntity<SupervisorDto> createSupervisor(@RequestBody CreateSupervisorRequest request){
        return ResponseEntity.ok(supervisorService.createSupervisor(request));
    }

    @GetMapping("/getSupervisor/{id}")
    public ResponseEntity<SupervisorDto> getSupervisor(@PathVariable int id){
        return ResponseEntity.ok(supervisorService.getSupervisorById(id));
    }

    @PutMapping("/updateSupervisor/{id}")
    public ResponseEntity<SupervisorDto> updateSupervisor(@RequestBody UpdateSupervisorRequest request,
                                                          @PathVariable int id){
        return ResponseEntity.ok(supervisorService.updateSupervisor(request, id));
    }

    @DeleteMapping("/deleteSupervisor/{id}")
    public ResponseEntity<String > deleteSupervisor(@PathVariable int id){
        return ResponseEntity.ok(supervisorService.deleteSupervisor(id));
    }

}

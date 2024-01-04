package com.ky.dbmanagementsystem.controller;

import com.ky.dbmanagementsystem.dto.CoSupervisorDto;
import com.ky.dbmanagementsystem.request.create.CreateCoSupervisorRequest;
import com.ky.dbmanagementsystem.request.update.UpdateCoSupervisorRequest;
import com.ky.dbmanagementsystem.service.CoSupervisorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/coSupervisor")
public class CoSupervisorController {
    private final CoSupervisorService coSupervisorService;

    public CoSupervisorController(CoSupervisorService coSupervisorService) {
        this.coSupervisorService = coSupervisorService;
    }

    @PostMapping("/createCoSupervisor")
    public ResponseEntity<CoSupervisorDto> createCoSupervisor(@RequestBody CreateCoSupervisorRequest request){
        return ResponseEntity.ok(coSupervisorService.createCoSupervisor(request));
    }

    @GetMapping("/getCoSupervisor/{id}")
    public ResponseEntity<CoSupervisorDto> getCoSupervisor(@PathVariable String id){
        return ResponseEntity.ok(coSupervisorService.getCoSupervisorById(id));
    }

    @PutMapping("/updateCoSupervisor/{id}")
    public ResponseEntity<CoSupervisorDto> createCoSupervisor(@RequestBody UpdateCoSupervisorRequest request,
                                                              @PathVariable String id){
        return ResponseEntity.ok(coSupervisorService.updateCoSupervisor(request, id));
    }

    @DeleteMapping("/deleteCoSupervisor/{id}")
    public ResponseEntity<String> deleteCoSupervisor(@PathVariable String id){
        return ResponseEntity.ok(coSupervisorService.deleteSupervisor(id));
    }
}

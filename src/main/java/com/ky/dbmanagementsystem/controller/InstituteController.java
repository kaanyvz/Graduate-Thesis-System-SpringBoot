package com.ky.dbmanagementsystem.controller;

import com.ky.dbmanagementsystem.dto.InstituteDto;
import com.ky.dbmanagementsystem.request.create.CreateInstituteRequest;
import com.ky.dbmanagementsystem.service.InstituteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/institute")
public class InstituteController {
    private final InstituteService instituteService;

    public InstituteController(InstituteService instituteService) {
        this.instituteService = instituteService;
    }

    @PostMapping("/createInstitute")
    public ResponseEntity<InstituteDto> createInstitute(@RequestBody CreateInstituteRequest request){
        return ResponseEntity.ok(instituteService.createInstitute(request));
    }

    @GetMapping("/getAllInstitutes")
    public ResponseEntity<List<InstituteDto>> getAllInstitutes(){
        return ResponseEntity.ok(instituteService.getAllInstitutes());
    }

    @GetMapping("/getAllInstitutesByUniversityName")
    public ResponseEntity<List<InstituteDto>> getAllInstitutesByUniversityName(@RequestParam String universityName){
        return ResponseEntity.ok(instituteService.getInstitutesByUniversityName(universityName));
    }

    @GetMapping("/getInstitute/{id}")
    public ResponseEntity<InstituteDto> getAllInstituteById(@PathVariable  int id){
        return ResponseEntity.ok(instituteService.getInstituteById(id));
    }

    @DeleteMapping("/deleteInstitute/{id}")
    public ResponseEntity<String> deleteInstituteById(@PathVariable int id){
        return ResponseEntity.ok(instituteService.deleteInstituteById(id));
    }
}

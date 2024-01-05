package com.ky.dbmanagementsystem.controller;

import com.ky.dbmanagementsystem.dto.UniversityDto;
import com.ky.dbmanagementsystem.request.create.CreateUniversityRequest;
import com.ky.dbmanagementsystem.service.UniversityService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/university")
//@PreAuthorize("hasRole('ADMIN')")
public class UniversityController {
    private final UniversityService universityService;

    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @GetMapping("/getUniversity/{id}")
    public ResponseEntity<UniversityDto> getUniversity(@PathVariable int id){
        return ResponseEntity.ok(universityService.getUniversityById(id));
    }

    @GetMapping("/getAllUniversities")
    public ResponseEntity<List<UniversityDto>> getAllUniversities(){
        return ResponseEntity.ok(universityService.getAllUniversities());
    }

    @GetMapping("/findUniversityByName")
    public ResponseEntity<UniversityDto> findUniversityByName(@RequestParam String name){
        return ResponseEntity.ok(universityService.findUniversityByName(name));
    }


    @PostMapping("/createUniversity")
//    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<UniversityDto> createUniversity(@RequestBody CreateUniversityRequest request){
        return ResponseEntity.ok(universityService.createUniversity(request));
    }

    @DeleteMapping("/deleteUniversity/{id}")
    public ResponseEntity<String> deleteUniversity(@PathVariable int id){
        return ResponseEntity.ok(universityService.deleteUniversityById(id));
    }
}

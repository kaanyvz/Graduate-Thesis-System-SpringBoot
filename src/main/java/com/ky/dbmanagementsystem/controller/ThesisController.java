package com.ky.dbmanagementsystem.controller;

import com.ky.dbmanagementsystem.dto.ThesisDto;
import com.ky.dbmanagementsystem.model.Thesis;
import com.ky.dbmanagementsystem.request.add.AddKeywordsToThesisRequest;
import com.ky.dbmanagementsystem.request.add.AddSubjectTopicsToThesisRequest;
import com.ky.dbmanagementsystem.request.add.AddSupervisorToThesisRequest;
import com.ky.dbmanagementsystem.request.create.CreateThesisRequest;
import com.ky.dbmanagementsystem.request.update.UpdateKeywordRequest;
import com.ky.dbmanagementsystem.request.update.UpdateSupervisorRequest;
import com.ky.dbmanagementsystem.request.update.UpdateThesisRequest;
import com.ky.dbmanagementsystem.service.ThesisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/thesis")
public class ThesisController {
    private final ThesisService thesisService;

    public ThesisController(ThesisService thesisService) {
        this.thesisService = thesisService;
    }

    @PostMapping("/createThesis")
    public ResponseEntity<ThesisDto> createThesis(@RequestBody CreateThesisRequest request){
        return ResponseEntity.ok(thesisService.createThesis(request));
    }

    @PostMapping("/createThesis/addSupervisor")
    public ResponseEntity<ThesisDto> addSupervisor(@RequestBody AddSupervisorToThesisRequest request){
        return ResponseEntity.ok(thesisService.addSupervisorToThesis(request));
    }

    @PostMapping("/createThesis/addKeywords")
    public ResponseEntity<String> addKeywordsToThesis(@RequestBody AddKeywordsToThesisRequest request){
        return ResponseEntity.ok(thesisService.addKeywordsToThesis(request));
    }

    @PostMapping("/createThesis/addSubjectTopics")
    public ResponseEntity<String> addSubjectTopicsToThesis(@RequestBody AddSubjectTopicsToThesisRequest request){
        return ResponseEntity.ok(thesisService.addSubjectTopicsToThesis(request));
    }

    @PostMapping("/updateThesis/updateKeywords")
    public ResponseEntity<String> updateKeywordsInThesis(@RequestBody AddKeywordsToThesisRequest request){
        return ResponseEntity.ok(thesisService.updateKeywordsInThesis(request));
    }



    @GetMapping("/getThesisById/{id}")
    public ResponseEntity<ThesisDto> getThesisById(@PathVariable int id){
        return ResponseEntity.ok(thesisService.getThesisById(id));
    }

    @GetMapping("/getByAuthor")
    public ResponseEntity<List<ThesisDto>> getThesesByAuthorName(@RequestParam String authorName,
                                                                 @RequestParam String authorLastname){
        return ResponseEntity.ok(thesisService.getThesesByAuthorName(authorName, authorLastname));
    }

    @GetMapping("/getThesisByAuthorMail")
    public ResponseEntity<List<ThesisDto>> getThesisByMail(@RequestParam String mail){
        return ResponseEntity.ok(thesisService.getThesisByMail(mail));
    }

    @GetMapping("/getByUniversity")
    public ResponseEntity<List<ThesisDto>> getThesesByUniversityName(@RequestParam String universityName){
        return ResponseEntity.ok(thesisService.getThesesByUniversityName(universityName));
    }

    @GetMapping("/searchTheses")
    public ResponseEntity<List<ThesisDto>> searchTheses(
            @RequestParam(required = false)String title,
            @RequestParam(required = false)String year,
            @RequestParam(required = false)String type,
            @RequestParam(required = false)String language,
            @RequestParam(required = false)String university,
            @RequestParam(required = false)String author,
            @RequestParam(required = false)String institute,
            @RequestParam(required = false)List<String> keywords

    ){
        System.out.println("Received year: " + year);
        return ResponseEntity.ok(thesisService.searchTheses(title, year, type, language, university,author, institute, keywords));
    }

    @GetMapping("/getByInstitute")
    public ResponseEntity<List<ThesisDto>> getByInstituteName(@RequestParam String instituteName){
        return ResponseEntity.ok(thesisService.getThesesByInstituteName(instituteName));
    }

    @GetMapping("/findByThesisNo")
    public ResponseEntity<ThesisDto> fetchThesisByThesisNo(@RequestParam Integer thesisNo){
        return ResponseEntity.ok(thesisService.fetchThesisByThesisNo(thesisNo));
    }


    @GetMapping("/getThesisByTitle")
    public ResponseEntity<List<ThesisDto>> getThesisByTitle(@RequestParam String title){
        return ResponseEntity.ok(thesisService.getThesisByTitle(title));
    }

    @GetMapping("/getSupervisorsInThesis")
    public ResponseEntity<Integer> getSupervisorsInThesis(@RequestParam int thesisId){
        return ResponseEntity.ok(thesisService.getCountOfSupervisorsInThesis(thesisId));
    }


    @GetMapping("/getAllTheses")
    public ResponseEntity<List<ThesisDto>> getAllTheses(){
        return ResponseEntity.ok(thesisService.getAllTheses());
    }

    @DeleteMapping("/deleteThesisById")
    public ResponseEntity<String> deleteById(@RequestParam int id){
        return ResponseEntity.ok(thesisService.deleteById(id));
    }

    @PutMapping("/updateThesisById")
    public ResponseEntity<ThesisDto> updateThesis(@RequestParam int id,
                                                  @RequestBody UpdateThesisRequest request){
        return ResponseEntity.ok(thesisService.updateThesis(id, request));
    }

    @PostMapping("/updateSupervisorsInThesis")
    public ResponseEntity<ThesisDto> updateSupervisorsInThesis(@RequestBody UpdateSupervisorRequest request) {
        return ResponseEntity.ok(thesisService.updateSupervisorsInThesis(request));
    }

}

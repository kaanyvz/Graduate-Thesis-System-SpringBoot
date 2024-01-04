package com.ky.dbmanagementsystem.controller;

import com.ky.dbmanagementsystem.dto.LanguageDto;
import com.ky.dbmanagementsystem.request.create.CreateLanguageRequest;
import com.ky.dbmanagementsystem.request.update.UpdateLanguageRequest;
import com.ky.dbmanagementsystem.service.LanguageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/language")
public class LanguageController {
    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @PostMapping("/createLanguage")
    public ResponseEntity<LanguageDto> createLanguage(@RequestBody CreateLanguageRequest request){
        return ResponseEntity.ok(languageService.createLanguage(request));
    }
    @GetMapping("/getLanguage/{id}")
    public ResponseEntity<LanguageDto> getLanguage(@PathVariable String id){
        return ResponseEntity.ok(languageService.getLanguageById(id));
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<LanguageDto>> getAllLanguages(){
        return ResponseEntity.ok(languageService.getAllLanguages());
    }
    @PutMapping("/updateLanguage/{id}")
    public ResponseEntity<LanguageDto> updateLanguage(@RequestBody UpdateLanguageRequest request,
                                                      @PathVariable String id){
        return ResponseEntity.ok(languageService.updateLanguage(request, id));
    }
    @DeleteMapping("/deleteLanguage/{id}")
    public ResponseEntity<String> deleteLanguage(@PathVariable String id){
        return ResponseEntity.ok(languageService.deleteLanguage(id));
    }

}

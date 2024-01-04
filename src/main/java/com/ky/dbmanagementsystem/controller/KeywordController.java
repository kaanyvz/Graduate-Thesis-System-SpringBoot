package com.ky.dbmanagementsystem.controller;

import com.ky.dbmanagementsystem.dto.KeywordDto;
import com.ky.dbmanagementsystem.request.create.CreateKeywordRequest;
import com.ky.dbmanagementsystem.request.update.UpdateKeywordRequest;
import com.ky.dbmanagementsystem.service.KeywordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/keyword")
public class KeywordController {
    private final KeywordService keywordService;

    public KeywordController(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @PostMapping("/createKeyword")
    public ResponseEntity<KeywordDto> createKeyword(@RequestBody CreateKeywordRequest createKeywordRequest){
        return ResponseEntity.ok(keywordService.createKeyword(createKeywordRequest));
    }

    @GetMapping("/getAllKeywords")
    public ResponseEntity<List<KeywordDto>> getAllKeywords(){
        return ResponseEntity.ok(keywordService.getAllKeywords());
    }

    @GetMapping("/getKeyword/{id}")
    public ResponseEntity<KeywordDto> getKeywordById(@PathVariable String id){
        return ResponseEntity.ok(keywordService.getKeywordById(id));
    }

    @DeleteMapping("/deleteKeyword/{id}")
    public ResponseEntity<String> createKeyword(@PathVariable String id){
        return ResponseEntity.ok(keywordService.deleteKeyword(id));
    }

    @PutMapping("/updateKeyword/{id}")
    public ResponseEntity<KeywordDto> updateKeyword(@RequestBody UpdateKeywordRequest request,
                                                    @PathVariable String id){
        return ResponseEntity.ok(keywordService.updateKeyword(request, id));
    }

}

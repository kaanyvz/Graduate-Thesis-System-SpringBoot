package com.ky.dbmanagementsystem.controller;

import com.ky.dbmanagementsystem.dto.SubjectTopicDto;
import com.ky.dbmanagementsystem.service.SubjectTopicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/subjectTopic")
public class SubjectTopicController {
    private final SubjectTopicService subjectTopicService;

    public SubjectTopicController(SubjectTopicService subjectTopicService) {
        this.subjectTopicService = subjectTopicService;
    }

    @GetMapping("/getTopic/{id}")
    public ResponseEntity<SubjectTopicDto> getSubjectTopic(@PathVariable int id){
        return ResponseEntity.ok(subjectTopicService.getSubjectTopicById(id));
    }

    @GetMapping("/getAllTopics")
    public ResponseEntity<List<SubjectTopicDto>> getAllSubjectTopic(){
        return ResponseEntity.ok(subjectTopicService.getAllTopics());
    }

    @DeleteMapping("/deleteTopic/{id}")
    public ResponseEntity<String> deleteSubjectTopic(@PathVariable int id){
        return ResponseEntity.ok(subjectTopicService.deleteSubjectTopic(id));
    }
}

package com.ky.dbmanagementsystem.service;

import com.ky.dbmanagementsystem.dto.SubjectTopicDto;
import com.ky.dbmanagementsystem.mapper.SubjectTopicMapper;
import com.ky.dbmanagementsystem.model.CoSupervisor;
import com.ky.dbmanagementsystem.model.SubjectTopic;
import com.ky.dbmanagementsystem.repository.SubjectTopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectTopicService {
    private final SubjectTopicRepository subjectTopicRepository;
    private final SubjectTopicMapper subjectTopicMapper;

    public SubjectTopicService(SubjectTopicRepository subjectTopicRepository, SubjectTopicMapper subjectTopicMapper) {
        this.subjectTopicRepository = subjectTopicRepository;
        this.subjectTopicMapper = subjectTopicMapper;
    }

    public SubjectTopicDto getSubjectTopicById(int id){
        SubjectTopic subjectTopic = subjectTopicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject topic could not found"));

        return subjectTopicMapper.subjectTopicToSubjectTopicDto(subjectTopic);
    }

    public SubjectTopic getSubjectTopic(int id){
        return subjectTopicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubjectTopic does not exist..."));
    }

    public List<SubjectTopicDto> getAllTopics(){
        return subjectTopicRepository.findAll()
                .stream()
                .map(subjectTopicMapper::subjectTopicToSubjectTopicDto)
                .collect(Collectors.toList());
    }

    public String deleteSubjectTopic(int id){
        SubjectTopic subjectTopic = subjectTopicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject topic could not found"));

        subjectTopicRepository.delete(subjectTopic);

        return subjectTopic.getName() + " deleted successfully from the system";
    }
}

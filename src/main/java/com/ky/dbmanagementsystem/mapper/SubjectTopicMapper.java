package com.ky.dbmanagementsystem.mapper;

import com.ky.dbmanagementsystem.dto.SubjectTopicDto;
import com.ky.dbmanagementsystem.model.SubjectTopic;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class SubjectTopicMapper {

    public SubjectTopicDto subjectTopicToSubjectTopicDto(SubjectTopic subjectTopic){
        return SubjectTopicDto.builder()
                .id(subjectTopic.getId())
                .name(subjectTopic.getName())
                .build();
    }
}

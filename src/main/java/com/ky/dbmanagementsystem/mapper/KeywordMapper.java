package com.ky.dbmanagementsystem.mapper;

import com.ky.dbmanagementsystem.dto.KeywordDto;
import com.ky.dbmanagementsystem.model.Keyword;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class KeywordMapper {


    public KeywordDto keywordToKeywordDto(Keyword keyword){
        return KeywordDto.builder()
                .id(keyword.getId())
                .name(keyword.getName())
                .build();
    }
}

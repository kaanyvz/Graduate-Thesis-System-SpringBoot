package com.ky.dbmanagementsystem.mapper;

import com.ky.dbmanagementsystem.dto.LanguageDto;
import com.ky.dbmanagementsystem.model.Language;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class LanguageMapper {


    public LanguageDto languageToLanguageDto(Language language){
        return LanguageDto.builder()
                .id(language.getId())
                .name(language.getName())
                .build();
    }
}

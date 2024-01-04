package com.ky.dbmanagementsystem.mapper;

import com.ky.dbmanagementsystem.dto.AuthorDto;
import com.ky.dbmanagementsystem.model.Author;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AuthorMapper {

    public AuthorDto authorToAuthorDto(Author author){
        return AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .lastname(author.getLastName())
                .mail(author.getMail())
                .build();
    }
}

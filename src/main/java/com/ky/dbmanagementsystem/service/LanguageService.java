package com.ky.dbmanagementsystem.service;

import com.ky.dbmanagementsystem.dto.LanguageDto;
import com.ky.dbmanagementsystem.mapper.LanguageMapper;
import com.ky.dbmanagementsystem.model.Institute;
import com.ky.dbmanagementsystem.model.Language;
import com.ky.dbmanagementsystem.repository.LanguageRepository;
import com.ky.dbmanagementsystem.request.create.CreateLanguageRequest;
import com.ky.dbmanagementsystem.request.update.UpdateLanguageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LanguageService {
    private final LanguageRepository languageRepository;
    private final LanguageMapper languageMapper;

    public LanguageService(LanguageRepository languageRepository, LanguageMapper languageMapper) {
        this.languageRepository = languageRepository;
        this.languageMapper = languageMapper;
    }

    public LanguageDto createLanguage(CreateLanguageRequest request){
        Language language = Language.builder()
                .name(request.getName())
                .build();

        if(isLanguageAlreadyExists(request.getName())){
            throw new RuntimeException(request.getName() + " language is already saved in system.");
        }

        Language savedLanguage = languageRepository.save(language);

        return languageMapper.languageToLanguageDto(savedLanguage);
    }

    public LanguageDto getLanguageById(int id){
        Language language = languageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Language could not found by id..."));

        return languageMapper.languageToLanguageDto(language);
    }

    public Language getLanguage(int id){
        return languageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Language does not exist..."));
    }

    public List<LanguageDto> getAllLanguages(){
        return languageRepository.findAll()
                .stream()
                .map(languageMapper::languageToLanguageDto)
                .collect(Collectors.toList());
    }

    public String deleteLanguage(int id){
        Language language = languageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Language could not found by id..."));

        languageRepository.delete(language);
        return (language.getName() + " has deleted successfully.");
    }

    public LanguageDto updateLanguage(UpdateLanguageRequest request, int id){
        Language language = languageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Language could not found by id..."));

        if(isLanguageAlreadyExists(request.getName())){
            throw new RuntimeException("Cannot update language It's already exists.");
        }
        language.setName(request.getName());
        Language savedLanguage = languageRepository.save(language);
        return languageMapper.languageToLanguageDto(savedLanguage);

    }

    private boolean isLanguageAlreadyExists(String name){
        return languageRepository.existsByName(name);
    }

    public Language getLanguageByName(String languageName) {
        return languageRepository.findByName(languageName);
    }
}















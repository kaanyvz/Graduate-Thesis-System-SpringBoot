package com.ky.dbmanagementsystem.service;

import com.ky.dbmanagementsystem.dto.KeywordDto;
import com.ky.dbmanagementsystem.mapper.KeywordMapper;
import com.ky.dbmanagementsystem.model.Institute;
import com.ky.dbmanagementsystem.model.Keyword;
import com.ky.dbmanagementsystem.repository.KeywordRepository;
import com.ky.dbmanagementsystem.request.create.CreateKeywordRequest;
import com.ky.dbmanagementsystem.request.update.UpdateKeywordRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeywordService {
    private final KeywordRepository keywordRepository;
    private final KeywordMapper keywordMapper;

    public KeywordService(KeywordRepository keywordRepository, KeywordMapper keywordMapper) {
        this.keywordRepository = keywordRepository;
        this.keywordMapper = keywordMapper;
    }

    public KeywordDto createKeyword(CreateKeywordRequest createKeywordRequest){
        Keyword keyword = Keyword.builder()
                .name(createKeywordRequest.getName())
                .theses(new ArrayList<>())
                .build();

        Keyword savedKeyword = keywordRepository.save(keyword);
        return keywordMapper.keywordToKeywordDto(savedKeyword);
    }

    public KeywordDto getKeywordById(String id){
        Keyword keyword = keywordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Keyword invalid"));
        return keywordMapper.keywordToKeywordDto(keyword);
    }

    public Keyword getKeyword(String id){
        return keywordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Keyword does not exist..."));
    }


    public KeywordDto updateKeyword(UpdateKeywordRequest request, String id){
        Keyword keyword =  keywordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Keyword invalid."));
        keyword.setName(request.getName());
        Keyword savedKeyword = keywordRepository.save(keyword);
        return keywordMapper.keywordToKeywordDto(savedKeyword);
    }

    public String deleteKeyword(String id){
        Keyword keyword =  keywordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Keyword invalid."));

        keywordRepository.delete(keyword);

        return keyword.getName() + " deleted successfully.";
    }

    public List<KeywordDto> getAllKeywords(){
        return keywordRepository.findAll()
                .stream()
                .map(keywordMapper::keywordToKeywordDto)
                .collect(Collectors.toList());
    }

}

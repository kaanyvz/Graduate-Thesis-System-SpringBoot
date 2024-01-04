package com.ky.dbmanagementsystem.request.add;

import lombok.Getter;

import java.util.List;

@Getter
public class AddKeywordsToThesisRequest {
    private Integer thesisNo;
    private List<String> keywordNames ;
}

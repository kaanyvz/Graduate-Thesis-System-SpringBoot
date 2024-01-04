package com.ky.dbmanagementsystem.request.update;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateThesisRequest {
    private Integer thesisNo;
    private String thesis_title;
    private String thesis_abstract;
    private String thesis_type;
    private Integer numberOfPages;
    private String languageName;
    private String universityName;
    private String instituteName;
    private String authorName;
    private String authorLastname;
    private String coSupervisorName;
    private String coSupervisorLastname;
    private LocalDateTime thesisYear;
}

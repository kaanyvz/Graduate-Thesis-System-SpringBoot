package com.ky.dbmanagementsystem.exception.Duplication;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DuplicateUniversityException extends RuntimeException {
    public DuplicateUniversityException(String message){
        super(message);
    }
}

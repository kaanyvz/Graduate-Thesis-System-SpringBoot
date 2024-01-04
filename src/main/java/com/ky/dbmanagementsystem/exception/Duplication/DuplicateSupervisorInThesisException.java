package com.ky.dbmanagementsystem.exception.Duplication;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateSupervisorInThesisException extends RuntimeException{
    public DuplicateSupervisorInThesisException(String message){
        super(message);
    }

}

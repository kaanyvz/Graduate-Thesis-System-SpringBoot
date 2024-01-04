package com.ky.dbmanagementsystem.exception;

import com.ky.dbmanagementsystem.exception.Duplication.DuplicateEmailException;
import com.ky.dbmanagementsystem.exception.Duplication.DuplicateSubjectTopicException;
import com.ky.dbmanagementsystem.exception.Duplication.DuplicateSupervisorInThesisException;
import com.ky.dbmanagementsystem.exception.Duplication.DuplicateUniversityException;
import com.ky.dbmanagementsystem.exception.NotFound.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @NotNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NotNull HttpHeaders headers,
                                                                  @NotNull HttpStatusCode status,
                                                                  @NotNull WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(e -> {
            String fieldName = ((FieldError) e).getField();
            String errorMessage = e.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    //NOT FOUND EXCEPTIONS
    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<HttpResponse> authorNotFoundException(AuthorNotFoundException exception){
        return createHttpResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(UniversityNotFoundException.class)
    public ResponseEntity<HttpResponse> universityNotFoundException(UniversityNotFoundException exception){
        return createHttpResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(ThesisNotFoundException.class)
    public ResponseEntity<HttpResponse> thesisNotFoundException(ThesisNotFoundException exception){
        return createHttpResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(InstituteNotFoundException.class)
    public ResponseEntity<HttpResponse> instituteNotFoundException(InstituteNotFoundException exception){
        return createHttpResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(CoSupervisorNotFoundException.class)
    public ResponseEntity<HttpResponse> coSupervisorNotFoundException(CoSupervisorNotFoundException exception){
        return createHttpResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(SupervisorNotFoundException.class)
    public ResponseEntity<HttpResponse> supervisorNotFoundException(SupervisorNotFoundException exception){
        return createHttpResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }


    //500 EXCEPTIONS
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> generalExceptionHandler(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DuplicateSubjectTopicException.class)
    public ResponseEntity<HttpResponse> duplicateSubjectTopicException(DuplicateSubjectTopicException exception){
        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @ExceptionHandler(NonValidSubjectTopicException.class)
    public ResponseEntity<HttpResponse> nonValidSubjectTopicException(NonValidSubjectTopicException exception){
        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @ExceptionHandler(DuplicateUniversityException.class)
    public ResponseEntity<HttpResponse> duplicateUniversityException(DuplicateUniversityException exception){
        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<HttpResponse> duplicateEmailException(DuplicateEmailException exception){
        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @ExceptionHandler(DuplicateSupervisorInThesisException.class)
    public ResponseEntity<HttpResponse> duplicateSupervisorInThesisException(DuplicateSupervisorInThesisException exception){
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }





    /*private method to create response*/
    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message){
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}

package com.ky.dbmanagementsystem.controller;

import com.ky.dbmanagementsystem.dto.AuthorDto;
import com.ky.dbmanagementsystem.model.Author;
import com.ky.dbmanagementsystem.request.create.CreateAuthorRequest;
import com.ky.dbmanagementsystem.request.update.UpdateAuthorRequest;
import com.ky.dbmanagementsystem.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/author")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/createAuthor")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody CreateAuthorRequest request){
        return ResponseEntity.ok(authorService.createAuthor(request));
    }

    @GetMapping("/getAuthor/{id}")
    public ResponseEntity<AuthorDto> createAuthor(@PathVariable int id){
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @GetMapping("/getAllAuthors")
    public ResponseEntity<List<AuthorDto>> createAuthor(){
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @PutMapping("/updateAuthor/{id}")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody UpdateAuthorRequest request,
                                                  @PathVariable int id){
        return ResponseEntity.ok(authorService.updateAuthor(request, id));
    }

    @GetMapping("/getAuthorByName")
    public ResponseEntity<Author> getAuthorByName(@RequestParam String name,
                                                  @RequestParam String lastname){
        return ResponseEntity.ok(authorService.getAuthorByName(name, lastname));
    }
    @DeleteMapping("/createAuthor")
    public ResponseEntity<String> deleteAuthor(int id){
         return ResponseEntity.ok(authorService.deleteAuthorById(id));
    }
}

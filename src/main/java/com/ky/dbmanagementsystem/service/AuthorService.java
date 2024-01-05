package com.ky.dbmanagementsystem.service;

import com.ky.dbmanagementsystem.dto.AuthorDto;
import com.ky.dbmanagementsystem.exception.NotFound.AuthorNotFoundException;
import com.ky.dbmanagementsystem.exception.Duplication.DuplicateEmailException;
import com.ky.dbmanagementsystem.mapper.AuthorMapper;
import com.ky.dbmanagementsystem.model.Author;
import com.ky.dbmanagementsystem.repository.AuthorRepository;
import com.ky.dbmanagementsystem.request.create.CreateAuthorRequest;
import com.ky.dbmanagementsystem.request.update.UpdateAuthorRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public AuthorDto createAuthor(CreateAuthorRequest authorRequest){
        Author author = Author.builder()
                .mail(authorRequest.getMail())
                .name(authorRequest.getName())
                .lastName(authorRequest.getLastname())
                .build();

        if(checkMailAlreadyExists(authorRequest.getMail())){
            throw new DuplicateEmailException("This mail has already used before... Try to use another mail");
        }
        Author savedAuthor = authorRepository.save(author);
        return authorMapper.authorToAuthorDto(savedAuthor);
    }

    public AuthorDto updateAuthor(UpdateAuthorRequest request, int id){
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author cannot find..."));

        if (checkMailAlreadyExists(request.getMail())) {
            throw new DuplicateEmailException("Cannot update author: Email already exist");
        }

        author.setName(request.getName());
        author.setLastName(request.getLastname());
        author.setMail(request.getMail());

        Author savedAuthor = authorRepository.save(author);
        return authorMapper.authorToAuthorDto(savedAuthor);

    }

    public AuthorDto getAuthorById(int id){
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author does not exist..."));

        return authorMapper.authorToAuthorDto(author);
    }

    public Author getAuthor(int id){

        return authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author does not exist..."));
    }

    public List<AuthorDto> getAllAuthors(){
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::authorToAuthorDto)
                .collect(Collectors.toList());
    }

    public String deleteAuthorById(int id){
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author cannot find..."));
        authorRepository.delete(author);
        return ("Author " + author.getName() + " " + author.getLastName() + " has deleted from system.");
    }



    public Author getAuthorByName(String name, String lastname) {
        Author author = authorRepository.findByNameAndLastName(name, lastname);
        if(author == null){
            throw new AuthorNotFoundException("NULL!");
        }
        return author;
    }

    //PRIVATE METHODS//
    // Avoids duplication of mails
    private boolean checkMailAlreadyExists(String mail){
        return authorRepository.existsByMail(mail);
    }
}

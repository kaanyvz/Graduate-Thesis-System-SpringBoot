package com.ky.dbmanagementsystem.security;

import com.ky.dbmanagementsystem.model.Author;
import com.ky.dbmanagementsystem.repository.AuthorRepository;
import com.ky.dbmanagementsystem.request.ChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Service
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final AuthorRepository authorRepository;

    public void changePassword(ChangePasswordRequest request, Principal connectedUser){
        var author = (Author) ((UsernamePasswordAuthenticationToken)connectedUser).getPrincipal();

        if(!passwordEncoder.matches(request.getCurrentPassword(), author.getPassword())){
            throw new IllegalStateException("Wrong password..");
        }
        if(!request.getNewPassword().equals(request.getConfirmationPassword())){
            throw new IllegalStateException("Passwords do not match with each other..");
        }

        author.setPassword(passwordEncoder.encode(request.getNewPassword()));
        authorRepository.save(author);
    }
}

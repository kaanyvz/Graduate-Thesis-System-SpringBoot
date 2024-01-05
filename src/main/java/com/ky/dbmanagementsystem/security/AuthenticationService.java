package com.ky.dbmanagementsystem.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ky.dbmanagementsystem.enumeration.Role;
import com.ky.dbmanagementsystem.enumeration.TokenType;
import com.ky.dbmanagementsystem.jwt.JwtService;
import com.ky.dbmanagementsystem.model.Author;
import com.ky.dbmanagementsystem.model.Token;
import com.ky.dbmanagementsystem.repository.AuthorRepository;
import com.ky.dbmanagementsystem.repository.TokenRepository;
import com.ky.dbmanagementsystem.request.AuthenticationRequest;
import com.ky.dbmanagementsystem.request.RegisterRequest;
import com.ky.dbmanagementsystem.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthorRepository authorRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request){
        Role userRole = determineUserRole(request.getEmail());
        if(isMailAlreadyPresent(request.getEmail())){
            throw new RuntimeException("Email has already exists in the system..." + request.getEmail());
        }

        var author = Author.builder()
                .name(request.getFirstName())
                .lastName(request.getLastName())
                .mail(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        var savedAuthor = authorRepository.save(author);
        var jwtToken = jwtService.generateToken(author);
        var refreshToken = jwtService.generateRefreshToken(author);
        saveAuthorToken(savedAuthor, jwtToken);

        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .role(userRole.toString())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        var author = authorRepository.findByMail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Author could not found by username" + request.getEmail()));

        var jwtToken = jwtService.generateToken(author);
        var refreshToken = jwtService.generateRefreshToken(author);
        revokeAllAuthorTokens(author);
        saveAuthorToken(author, jwtToken);
        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .role(author.getRole().toString())
                .build();
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException{

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String authorMail;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        refreshToken = authHeader.substring(7);
        authorMail = jwtService.extractUsername(refreshToken);
        if(authorMail != null){
            var author = this.authorRepository.findByMail(authorMail)
                    .orElseThrow();
            if(jwtService.isTokenValid(refreshToken, author)){
                var accessToken = jwtService.generateToken(author);
                revokeAllAuthorTokens(author);
                saveAuthorToken(author, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    //PRIVATE METHODS
    private void revokeAllAuthorTokens(Author author){
        var validAuthorTokens = tokenRepository.findAllValidTokensByAuthor(author.getId());
        if(validAuthorTokens.isEmpty()){
            return;
        }
        validAuthorTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validAuthorTokens);
    }


    private void saveAuthorToken(Author author, String jwtToken){
        var token = Token.builder()
                .author(author)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    public boolean isAdminAlreadyRegistered() {
        return isMailAlreadyPresent("admin@gmail.com");
    }

    private boolean isMailAlreadyPresent(String mail){
        return authorRepository.existsByMail(mail);
    }

    private Role determineUserRole(String userMail){
        if("admin@gmail.com".equalsIgnoreCase(userMail)){
            return Role.ADMIN;
        }else {
            return Role.USER;
        }
    }

}

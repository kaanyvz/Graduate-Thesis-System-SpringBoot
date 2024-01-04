package com.ky.dbmanagementsystem.repository;

import com.ky.dbmanagementsystem.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, String> {

    @Query("""
    select t from Token t inner join Author u on t.author.id = u.id
    where u.id = :userId and (t.expired = false or t.revoked = false)
""")
    List<Token> findAllValidTokensByAuthor(String userId);

    Optional<Token> findByToken(String token);

}

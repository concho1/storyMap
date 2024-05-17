package com.story.concho.model.repository;

import com.story.concho.model.domain.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token, String> {
    Optional<Token> findByTokenEmailAndTokenValue(String email, int tokenValue);

    Optional<Token> findByTokenEmail(String email);
}

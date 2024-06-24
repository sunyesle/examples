package com.sunyesle.springbootdockercompose.repository;

import com.sunyesle.springbootdockercompose.entity.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token, String> {
    //@Id 또는 @Indexed 어노테이션을 적용한 프로퍼티들만 CrudRepository가 제공하는 findBy~ 구문 사용가능
    Optional<Token> findById(String id);

    Optional<Token> findByToken(String token);
}

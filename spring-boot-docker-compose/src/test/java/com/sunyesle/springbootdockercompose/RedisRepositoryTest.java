package com.sunyesle.springbootdockercompose;

import com.sunyesle.springbootdockercompose.entity.Token;
import com.sunyesle.springbootdockercompose.repository.TokenRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisRepositoryTest {
    @Autowired
    private TokenRepository tokenRepository;

    @Test
    void test(){
        Token token = new Token("1111", "asdfqwer1234", "USER", 600L);

        tokenRepository.save(token);

        Token savedToken1 = tokenRepository.findByToken("asdfqwer1234").orElseThrow();
        Token savedToken2 = tokenRepository.findById(token.getId()).orElseThrow();
        System.out.println(savedToken1);
        System.out.println(savedToken2);

        System.out.println(tokenRepository.count());

        tokenRepository.delete(token);

        System.out.println(tokenRepository.count());
    }
}

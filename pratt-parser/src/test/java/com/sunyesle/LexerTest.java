package com.sunyesle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {
    @Test
    void test(){
        Lexer lexer = new Lexer("1 + 2");

        System.out.println(lexer.toString());

        assertEquals(lexer.next().getValue(), '1'); // next(): 현재 토큰을 가져오고 pos++
        assertEquals(lexer.peek().getValue(), '+'); // peek(): 다음 토큰 확인
        assertEquals(lexer.next().getValue(), '+');
        assertEquals(lexer.next().getValue(), '2');
        assertEquals(lexer.next().getType(), TokenType.EOF); // 끝에 도달했을 경우 TokenType.EOF 반환
    }
}
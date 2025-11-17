package com.sunyesle;

import java.util.List;

public class Lexer {
    private final List<Token> tokens;
    private int pos = 0;

    Lexer(String input) {
        tokens = input.chars()
                .mapToObj(c -> (char) c)
                .filter(c -> ' ' != c)
                .map(c -> {
                    if ('0' <= c && c <= '9') {
                        return Token.atom(c);
                    } else {
                        return Token.op(c);
                    }
                }).toList();
    }

    /**
     * 다음 위치의 토큰을 반환하고, 위치는 변화시키지 않는다.
     */
    public Token peek() {
        if (isEOF()) return Token.eof();
        return tokens.get(pos);
    }

    /**
     * 현재 위치의 토큰을 반환하고, 위치를 1 증가시킨다.
     */
    public Token next() {
        if (isEOF()) return Token.eof();
        return tokens.get(pos++);
    }

    private boolean isEOF() {
        return pos >= tokens.size();
    }

    @Override
    public String toString() {
        return "Lexer{" +
                "tokens=" + tokens +
                '}';
    }
}

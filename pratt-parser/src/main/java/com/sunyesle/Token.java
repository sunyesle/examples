package com.sunyesle;

public class Token {
    private final TokenType type;
    private final Character value;

    private Token(TokenType type, Character value) {
        this.type = type;
        this.value = value;
    }

    public static Token atom(char c) {
        return new Token(com.sunyesle.TokenType.ATOM, c);
    }

    public static Token op(char c) {
        return new Token(com.sunyesle.TokenType.OP, c);
    }

    public static Token eof() {
        return new Token(com.sunyesle.TokenType.EOF, null);
    }

    public TokenType getType() {
        return type;
    }

    public Character getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type.name() +
                ", value=" + value +
                '}';
    }
}

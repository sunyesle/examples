package com.sunyesle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void Test(){
        Lexer lexer = new Lexer("1 + 2 * 4");
        Parser parser = new Parser(lexer);
        Expr expr = parser.parseExpr(0);

        assertEquals(expr.toString(), "(+ 1 (* 2 4))");
    }
}
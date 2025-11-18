package com.sunyesle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EvaluatorTest {

    @Test
    void test() {
        Lexer lexer = new Lexer("1 + 3 * 2 - 4 / 2");
        Parser parser = new Parser(lexer);
        Expr expr = parser.parseExpr(0);

        int result = Evaluator.eval(expr);

        assertEquals(result, 5);
    }
}
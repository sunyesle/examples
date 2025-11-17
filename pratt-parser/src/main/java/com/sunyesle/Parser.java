package com.sunyesle;

import java.util.List;

public class Parser {
    private final Lexer lexer;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public Expr parseExpr(int minBp) {
        Token atomToken = lexer.next();
        Expr lhs;
        if (atomToken.getType() == TokenType.ATOM) {
            lhs = new Expr.Atom(atomToken.getValue());
        } else {
            throw new RuntimeException("bad token: " + atomToken);
        }

        while (true) {
            Token opToken = lexer.peek();
            if (opToken.getType() == TokenType.EOF) {
                break;
            }
            if (opToken.getType() != TokenType.OP) {
                throw new RuntimeException("bad token: " + opToken);
            }

            int[] bp = BindingPower.infix(opToken.getValue());
            int lBp = bp[0];
            int rBp = bp[1];

            if (lBp < minBp) {
                break;
            }

            lexer.next();
            Expr rhs = parseExpr(rBp);
            lhs = new Expr.Operation(opToken.getValue(), List.of(lhs, rhs));
        }
        return lhs;
    }
}

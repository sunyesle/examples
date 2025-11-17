package com.sunyesle;

import java.util.List;

/**
 * 수식을 표현하는 AST(Abstract Syntax Tree) 노드이다.<br>
 * - Atom: 리프 노드, 숫자<br>
 * - Operation: 내부 노드, 연산자와 피연산자 리스트
 */
public abstract class Expr {

    static class Atom extends Expr {
        private final char value;

        public Atom(char value) {
            this.value = value;
        }

        public char getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "" + value;
        }
    }

    static class Operation extends Expr {
        private final char op;
        private final List<Expr> values;

        public Operation(char op, List<Expr> values) {
            this.op = op;
            this.values = values;
        }

        public char getOp() {
            return op;
        }

        public List<Expr> getValues() {
            return values;
        }

        @Override
        public String toString() {
            return "(" + op + ' ' +
                    String.join(" ", values.stream().map(Object::toString).toList()) +
                    ')';
        }
    }
}

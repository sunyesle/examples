package com.sunyesle;

public class Evaluator {
    static int eval(Expr expr) {
        if(expr instanceof Expr.Atom atom){
            return atom.getValue() - '0';
        }else if(expr instanceof Expr.Operation op){
            int lhs = Evaluator.eval(op.getValues().get(0));
            int rhs = Evaluator.eval(op.getValues().get(1));

            switch (op.getOp()){
                case '+' : return lhs + rhs;
                case '-' : return lhs - rhs;
                case '*' : return lhs * rhs;
                case '/' : return lhs / rhs;
                default: throw new RuntimeException("bad operator: " + op.getOp());
            }
        }
        throw new RuntimeException("bad expr: " + expr);
    }
}

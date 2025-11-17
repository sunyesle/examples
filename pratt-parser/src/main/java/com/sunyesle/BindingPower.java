package com.sunyesle;

class BindingPower {
    static int[] infix(char op) {
        return switch (op) {
            case '+', '-' -> new int[]{1, 2};
            case '*', '/' -> new int[]{3, 4};
            default -> throw new RuntimeException("unknown op: " + op);
        };
    }
}

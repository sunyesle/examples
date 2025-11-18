package com.sunyesle;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                Lexer lexer = new Lexer(input);
                Parser parser = new Parser(lexer);

                Expr expr = parser.parseExpr(0);
                System.out.println("AST: " + expr);

                int result = Evaluator.eval(expr);
                System.out.println("result: " + result);
            } catch (Exception e) {
                System.out.println("error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
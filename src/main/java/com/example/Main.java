package com.example;

public class Main {
    public static void main(String[] args) {

        String equation1 = "(f_o(X, Y) ~= 0.4 f_o(b_o, a_o))";
        String relations = "f(a, b) = 0.5";
        
        com.example.Sim.disjunction = com.example.parser.DisjunctionParser.parse(equation1);
        com.example.parser.RelationsParser.parse(relations);

        System.out.println("Solution: " + com.example.Sim.solve());


    }
}
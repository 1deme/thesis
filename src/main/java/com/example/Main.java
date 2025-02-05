package com.example;

public class Main {
    public static void main(String[] args) {

        String equation1 = "(a_o(X, Y) ~= 0.3 b_o(a_o, X) /\\ M ~= 0.5 b_o)";
        String relations = "(a_o, b_o, 0.4)";
        
        com.example.Sim.disjunction = com.example.parser.DisjunctionParser.parse(equation1);
        com.example.parser.RelationsParser.parse(relations);

        System.out.println(com.example.Sim.solve());

        com.example.Sim.solution.clear();

    }
}
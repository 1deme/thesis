package com.example;

public class Main {
    public static void main(String[] args) {

        String equation1 = "b_o(X) ~= 0.3 f_o(a_o) ";
        String relations = "(a_o, b_o, 0.3)";
        
        com.example.Sim.disjunction = com.example.parser.DisjunctionParser.parse(equation1);
        com.example.parser.RelationsParser.parse(relations);

        System.out.println(com.example.Sim.solve());


    }
}
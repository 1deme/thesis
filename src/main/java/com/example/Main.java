package com.example;

public class Main {
    public static void main(String[] args) {

        String equation1 = "(f_u(X, f_o(X, b_o), Y, h_o(X, Y)) ~= 0.4 g_u(g_o(a_o, Y), b_o))";
        String relations = "(f_u, g_u, 0.6), (f_o, g_o, 0.5), (h_u, g_o, 0.3)";

        // String equation1 = "(f_u(X, b_o) ~= 0.4 g_u(a_o, b_o))";
        // String relations = "(f_u, g_u, 0.6)";

        // String equation1 = "(f_u(X, Y) ~= 0.4 g_u(a_o, b_o))";
        // String relations = "(f_u, g_u, 0.6)";
        
        com.example.Sim.disjunction = com.example.parser.DisjunctionParser.parse(equation1);
        com.example.parser.RelationsParser.parse(relations);

        System.out.println(com.example.Sim.solve());

        com.example.Sim.solution.clear();

    }
}
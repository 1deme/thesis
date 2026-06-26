package com.example;

import com.example.algorithm.SolPc;
import com.example.algorithm.SolSc;
import com.example.algorithm.SolTransformation;
import org.teavm.jso.JSExport;


public class Main {

    public static void main(String[] args) throws Exception {


    }

    @JSExport
    public static String solve(String equation1, String relations, boolean isProximity) {
        com.example.algorithm.SolveSim.disjunction = com.example.parser.DisjunctionParser.parse(equation1);
        com.example.parser.RelationsParser.parse(relations);

        String result = "";

        if (!isProximity && !com.example.relations.relationCollection.checkTransitivity()) {
            result = "The relation is not transitive.";
        } else {
            SolTransformation solverInstance = isProximity ? new SolPc() : new SolSc();
            result = com.example.algorithm.SolveSim.solve(solverInstance);
        }

        com.example.algorithm.SolveSim.solution.clear();
        com.example.algorithm.SolveSim.disjunction.conjunctions.clear();
        com.example.relations.relationCollection.collection.clear();

        return result;
    }
}
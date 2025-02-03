package com.example.parser;
import java.util.ArrayList;
import java.util.List;

import com.example.dnf.Conjunction;
import com.example.dnf.Disjunction;
import com.example.predicates.SimilarityPredicate;

public class DisjunctionParser {

    public static Disjunction parse(String input) {
        input = input.trim();

        String[] conjunctionStrings = input.split("\\s*\\\\/\\s*");

        List<Conjunction> conjunctions = new ArrayList<>();
        for (String conjunctionStr : conjunctionStrings) {
            conjunctionStr = conjunctionStr.trim().replaceAll("^\\(|\\)$", "").trim();
            Conjunction conjunction = parseConjunction(conjunctionStr);
            conjunctions.add(conjunction);
        }

        return new Disjunction(conjunctions);
    }

    private static Conjunction parseConjunction(String input) {
        String[] predicateStrings = input.split("\\s*/\\\\\\s*");

        List<SimilarityPredicate> predicates = new ArrayList<>();
        for (String predicateStr : predicateStrings) {
            predicateStr = predicateStr.trim();
            SimilarityPredicate predicate = SimilarityPredicateParser.parse(predicateStr);
            predicates.add(predicate);
        }

        return new Conjunction(predicates);
    }

    public static void main(String[] args) {
        try {
            String input = "(X ~= 0.5 Y /\\ f_u(Z) ~= 0.7 W) \\/ (A ~= 0.3 B)";
            Disjunction disjunction = DisjunctionParser.parse(input);
            System.out.println("Parsed Disjunction: " + disjunction);

            String input2 = "(X ~= 0.5 Y \\/ X ~= 0.5 Y) \\/ (A ~= 0.3 B /\\ C ~= 0.8 D)";
            Disjunction disjunction2 = DisjunctionParser.parse(input2);
            System.out.println("Parsed Disjunction: " + disjunction2);

            String invalidInput = "(X ~= 0.5 Y /\\ invalid) \\/ (A ~= 0.3 B)";
            Disjunction invalidDisjunction = DisjunctionParser.parse(invalidInput);
            System.out.println("Parsed Disjunction: " + invalidDisjunction);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
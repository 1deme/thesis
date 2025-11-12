package com.example.parser;
import java.util.ArrayList;
import java.util.List;

import com.example.dnf.Conjunction;
import com.example.dnf.Disjunction;
import com.example.predicates.SimilarityPredicate;

public class DisjunctionParser {

    public static Disjunction parse(String input) {
        input = removeOuterParentheses(input);

        String[] conjunctionStrings = input.split("\\s*\\\\/\\s*");

        List<Conjunction> conjunctions = new ArrayList<>();
        for (String conjunctionStr : conjunctionStrings) {
            conjunctionStr = removeOuterParentheses(conjunctionStr);
            Conjunction conjunction = parseConjunction(conjunctionStr);
            conjunctions.add(conjunction);
        }

        return new Disjunction(conjunctions);
    }

    public static Conjunction parseConjunction(String input) {

        if (input.equalsIgnoreCase("true")) {
            return new Conjunction(new ArrayList<>());
        }
        
        String[] predicateStrings = input.split("\\s*/\\\\\\s*");

        List<SimilarityPredicate> predicates = new ArrayList<>();
        for (String predicateStr : predicateStrings) {
            predicateStr = removeOuterParentheses(predicateStr);
            SimilarityPredicate predicate = SimilarityPredicateParser.parse(predicateStr);
            predicates.add(predicate);
        }

        return new Conjunction(predicates);
    }

    private static String removeOuterParentheses(String s) {
        s = s.trim();
        if (s.startsWith("(") && s.endsWith(")")) {
            int depth = 0;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '(') depth++;
                else if (c == ')') depth--;
                if (depth == 0 && i < s.length() - 1) {
                    return s;
                }
            }
            return s.substring(1, s.length() - 1).trim();
        }
        return s;
    }
    

}
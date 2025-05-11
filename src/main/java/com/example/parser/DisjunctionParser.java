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

    public static Conjunction parseConjunction(String input) {
        String[] predicateStrings = input.split("\\s*/\\\\\\s*");

        List<SimilarityPredicate> predicates = new ArrayList<>();
        for (String predicateStr : predicateStrings) {
            predicateStr = predicateStr.trim();
            SimilarityPredicate predicate = SimilarityPredicateParser.parse(predicateStr);
            predicates.add(predicate);
        }

        return new Conjunction(predicates);
    }

}
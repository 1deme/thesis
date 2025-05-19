package com.example.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.constraintElements.Term;
import com.example.predicates.SimilarityPredicate;

public class SimilarityPredicateParser {

    public static SimilarityPredicate parse(String input) {
        input = input.trim();

        Pattern pattern = Pattern.compile("(.+) ~ ([0-9.]+) (.+)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {

            String term1Str = matcher.group(1).trim();
            Term term1 = TermParser.parse(term1Str);

            double cutValue = Double.parseDouble(matcher.group(2).trim());

            String term2Str = matcher.group(3).trim();
            Term term2 = TermParser.parse(term2Str);

            return new SimilarityPredicate(term1, term2, cutValue);
        } else {
            throw new IllegalArgumentException("Invalid similarity predicate format: " + input);
        }
    }

}
package com.example.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.constraintElements.Term;
import com.example.predicates.SimilarityPredicate;

public class SimilarityPredicateParser {

    // Parse a string into a SimilarityPredicate object
    public static SimilarityPredicate parse(String input) {
        // Trim any leading or trailing whitespace
        input = input.trim();

        // Use regex to extract the two terms and the CutValue
        Pattern pattern = Pattern.compile("(.+) ~= ([0-9.]+) (.+)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            // Extract the first term
            String term1Str = matcher.group(1).trim();
            Term term1 = TermParser.parse(term1Str);

            // Extract the CutValue
            double cutValue = Double.parseDouble(matcher.group(2).trim());

            // Extract the second term
            String term2Str = matcher.group(3).trim();
            Term term2 = TermParser.parse(term2Str);

            // Create and return the SimilarityPredicate
            return new SimilarityPredicate(term1, term2, cutValue);
        } else {
            throw new IllegalArgumentException("Invalid similarity predicate format: " + input);
        }
    }

    public static void main(String[] args) {
        try {
            // Test valid input
            String input = "X ~= 0.5 f_u(Y)";
            SimilarityPredicate predicate = SimilarityPredicateParser.parse(input);
            System.out.println("Parsed SimilarityPredicate: " + predicate);

            // Test another valid input
            String input2 = "g_o(X, Y) ~= 0.75 Z";
            SimilarityPredicate predicate2 = SimilarityPredicateParser.parse(input2);
            System.out.println("Parsed SimilarityPredicate: " + predicate2);

            // Test invalid input
            String invalidInput = "X ~= abc Y";
            SimilarityPredicate invalidPredicate = SimilarityPredicateParser.parse(invalidInput);
            System.out.println("Parsed SimilarityPredicate: " + invalidPredicate);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    

}
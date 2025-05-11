//package com.example.parser;
//
//import com.example.constraintElements.Term;
//import com.example.predicates.SimilarityPredicate;
//
//public class UnificationEquationParser {
//
//
//    public static SimilarityPredicate parse(String equation, double cutValue) {
//        String[] equationParts = equation.split("~=");
//        String left = equationParts[0].trim();
//        String right = equationParts[1].trim();
//
//        System.out.println(left);
//        System.out.println(right);
//
//        TermParser LeftExprParser = new TermParser(new Lexer(left).tokenize());
//        TermParser RightExprParser = new TermParser(new Lexer(right).tokenize());
//
//        Term leftTerm = LeftExprParser.parseTerm();
//        Term rightTerm  = RightExprParser.parseTerm();
//
//        return new SimilarityPredicate(leftTerm, rightTerm, 1, cutValue);
//    }
//
//}

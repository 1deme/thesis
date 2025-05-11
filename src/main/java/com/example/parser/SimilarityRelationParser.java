//package com.example.parser;
//
//import com.example.constraintElements.Element;
//import com.example.constraintElements.FunctionApplication;
//import com.example.constraintElements.FunctionConstant;
//import com.example.constraintElements.FunctionSymbol;
//import com.example.relations.Relation;
//
//import java.util.ArrayList;
//import java.util.List;
//public class SimilarityRelationParser {
//    private final List<Token> tokens;
//    private int current = 0;
//
//    public SimilarityRelationParser(List<Token> tokens) {
//        this.tokens = tokens;
//    }
//
//    public List<Relation> parseRelations() {
//        List<Relation> relations = new ArrayList<>();
//
//        while (!isAtEnd()) {
//            consume(TokenType.PAREN_OPEN, "Expected '(' at the start of a relation");
//            List<Element> arguments = new ArrayList<>();
//
//            do {
//                if (match(TokenType.FUNCTION_CONSTANT)) {
//                    arguments.add(new FunctionConstant(previous().value.charAt(0)));
//                    continue;
//                }
//                if (match(TokenType.ORDERED_FUNCTION) || match(TokenType.UNORDERED_FUNCTION)) {
//                    FunctionSymbol functionSymbol = new FunctionSymbol(previous().value.charAt(0));
//                    arguments.add(functionSymbol);
//                    continue;
//                }
//                throw new IllegalArgumentException("Unrecognized token: " + previous().value);
//
//            } while (match(TokenType.COMMA));
//
//            consume(TokenType.PAREN_CLOSE, "Expected ')' at the end of a relation");
//
//            if (arguments.size() < 2) {
//                throw new IllegalStateException("A relation must have at least two constants");
//            }
//
//            relations.add(new Relation(arguments.get(0),arguments.get(1),1, 0.5));
//        }
//
//        return relations;
//    }
//
//    private boolean match(TokenType type) {
//        if (check(type)) {
//            advance();
//            return true;
//        }
//        return false;
//    }
//
//    private boolean check(TokenType type) {
//        return !isAtEnd() && peek().type == type;
//    }
//
//    private Token advance() {
//        return tokens.get(current++);
//    }
//
//    private Token peek() {
//        return tokens.get(current);
//    }
//
//    private Token previous() {
//        return tokens.get(current - 1);
//    }
//
//    private boolean isAtEnd() {
//        return current >= tokens.size();
//    }
//
//    private void consume(TokenType type, String message) {
//        if (check(type)) {
//            advance();
//        } else {
//            throw new IllegalStateException(message);
//        }
//    }
//
//    public static void main(String[] args) {
//        // Example tokens: (a, b), (b, c), (c, d)
//        String input = "(f_u, b) (b, c) (c, d)";
//        List<Token> tokens = new Lexer(input).tokenize();
//
//        System.out.println(tokens);
//
//        SimilarityRelationParser parser = new SimilarityRelationParser(tokens);
//        List<Relation> relations = parser.parseRelations();
//
//        System.out.println("Parsed relations:");
//        for (Relation relation : relations) {
//            System.out.println(relation.toString());
//        }
//    }
//}

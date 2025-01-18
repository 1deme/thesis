package com.example.parser;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    private final String input;
    private int position = 0;

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (position < input.length()) {
            char current = input.charAt(position);

            if (Character.isAlphabetic(current)) {
                tokens.add(parseWord());
            } else if (Character.isDigit(current) || current == '.') {
                tokens.add(parseNumber());
            } else if (current == '(') {
                tokens.add(new Token(TokenType.PAREN_OPEN, "("));
                position++;
            } else if (current == ')') {
                tokens.add(new Token(TokenType.PAREN_CLOSE, ")"));
                position++;
            } else if (current == ',') {
                tokens.add(new Token(TokenType.COMMA, ","));
                position++;
            } else {
                position++; // Skip whitespace or invalid characters
            }
        }
        return tokens;
    }

    private Token parseWord() {
        int start = position;
        while (position < input.length() &&
                (Character.isAlphabetic(input.charAt(position)) || input.charAt(position) == '_')) {
            position++;
        }
        String word = input.substring(start, position);
        return word.endsWith("_u") || word.endsWith("_o")
                ? function(word)
                : isVariable(word) ? new Token(TokenType.VARIABLE, word)
                : new Token(TokenType.FUNCTION_CONSTANT, word);
    }

    private Token function(String word) {
        return word.endsWith("_u")
                ? new Token(TokenType.UNORDERED_FUNCTION, word)
                : new Token(TokenType.ORDERED_FUNCTION, word);
    }

    private Token parseNumber() {
        int start = position;
        boolean hasDecimalPoint = false;

        while (position < input.length() &&
                (Character.isDigit(input.charAt(position)) || input.charAt(position) == '.')) {
            if (input.charAt(position) == '.') {
                if (hasDecimalPoint) {
                    throw new IllegalArgumentException("Invalid number format with multiple decimal points");
                }
                hasDecimalPoint = true;
            }
            position++;
        }

        String number = input.substring(start, position);
        return new Token(TokenType.NUMBER, number);
    }

    private boolean isVariable(String word) {
        return word.matches("^[u-zU-Z].*");
    }

    public static void main(String[] args) {
        String input = "(a,b,0.5), (k,a,0.4)";
        List<Token> tokens = new Lexer(input).tokenize();
        System.out.println(tokens);
    }
}

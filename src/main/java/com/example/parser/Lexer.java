package com.example.parser;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    private final String input;
    private int position = 0;

    Lexer(String input){
        this.input = input;
    }

    public List<Token> tokenize(){
        List<Token> tokens = new ArrayList<>();
        while (position < input.length()) {
            char current = input.charAt(position);
            if (Character.isAlphabetic(current)) {
                tokens.add(parseWord());
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
        while (position < input.length() && Character.isAlphabetic(input.charAt(position))) {
            position++;
        }
        String word = input.substring(start, position);
        return word.endsWith("_u") || word.endsWith("_o")
                ? function(word)
                : new Token(TokenType.VARIABLE, word);
    }

    private Token function(String word){
        return word.endsWith("_u")
                ? new Token(TokenType.UNORDERED_FUNCTION, word)
                : new Token(TokenType.ORDERED_FUNCTION, word);
    }

    public static void main(String[] args) {
        String f = "fu(a,b)";
        List<Token> tokens = new Lexer(f).tokenize();
        System.out.println(tokens);
    }
}

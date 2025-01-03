package com.example.parser;

import com.example.constraintElements.FunctionApplication;
import com.example.constraintElements.FunctionSymbol;
import com.example.constraintElements.Term;
import com.example.constraintElements.TermVariable;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int current = 0;

    Parser(List<Token> tokens){
        this.tokens = tokens;
    }

    public Term parse(){
        return null;
    }
    private Term parseFunction(boolean isOrdered){
        String functionName = previous().value;
        consume(TokenType.PAREN_OPEN, "Expected '(' after function name");

        List<Term> arguments = new ArrayList<>();
        if(!check(TokenType.PAREN_CLOSE)){
            do {
                arguments.add(parseTerm());
            }while (match(TokenType.COMMA));
        }
        consume(TokenType.PAREN_CLOSE,"Expected ')' after function argument");
        FunctionSymbol functionSymbol = new FunctionSymbol(functionName.charAt(0));
        return new FunctionApplication(functionSymbol,arguments.toArray(new Term[0]),isOrdered);
    }

    private Term parseTerm(){
        if(match(TokenType.VARIABLE)){
            return new TermVariable(previous().value.charAt(0));
        }
        if(match(TokenType.ORDERED_FUNCTION)) {
            return parseFunction(true);
        }
        if(match(TokenType.UNORDERED_FUNCTION)){
            return parseFunction(false);
        }
        throw new IllegalStateException("Unexpected token: " + peek());
    }

    // Helper methods
    private boolean match(TokenType type) {
        if (check(type)) {
            advance();
            return true;
        }
        return false;
    }

    private boolean check(TokenType type) {
        return !isAtEnd() && peek().type == type;
    }

    private Token advance() {
        return tokens.get(current++);
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private boolean isAtEnd() {
        return current >= tokens.size();
    }

    private void consume(TokenType type, String message) {
        if (check(type)) {
            advance();
            return;
        }
        throw new IllegalStateException(message);
    }

    public static void main(String[] args) {
        String f = "fu(a,fu(a,b),gu(n,m),c)";
        List<Token> tokenList = new Lexer(f).tokenize();
        System.out.println(tokenList);
        Parser parser = new Parser(tokenList);
        Term term = parser.parseTerm();
        System.out.println(term);
    }

}

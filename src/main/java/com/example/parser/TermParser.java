package com.example.parser;

import com.example.constraintElements.FunctionApplication;
import com.example.constraintElements.FunctionSymbol;
import com.example.constraintElements.Term;
import com.example.constraintElements.TermVariable;
import com.example.predicates.SimilarityPredicate;

import java.util.ArrayList;
import java.util.List;

public class TermParser {
    private final List<Token> tokens;
    private int current = 0;

    TermParser(List<Token> tokens){
        this.tokens = tokens;
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



    public Term parseTerm(){
        if(match(TokenType.VARIABLE)){
            return new TermVariable(previous().value.charAt(0));
        }

        if (match(TokenType.FUNCTION_CONSTANT)){
            char value = previous().value.charAt(0);
            FunctionSymbol symbol = new FunctionSymbol(value);
            return new FunctionApplication(symbol,new Term[0], true);
        }

        if(match(TokenType.ORDERED_FUNCTION)) {
            return parseFunction(true);
        }
        if(match(TokenType.UNORDERED_FUNCTION)){
            return parseFunction(false);
        }
        throw new IllegalStateException("Unexpected token: " + peek());
    }

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
        String f = "f_u(a,f_u(a,b),g_u(n,m),c)";
        List<Token> tokenList = new Lexer(f).tokenize();
        System.out.println(tokenList);
        TermParser parser = new TermParser(tokenList);
        Term term = parser.parseTerm();
        System.out.println(term);



        String unificationProblem = "f_u(g_o(a),c) ~= f_u(x,b)";

        SimilarityPredicate similarityPredicate = UnificationEquationParser.parse(unificationProblem, 0.5);

        System.out.println(similarityPredicate);


    }

}

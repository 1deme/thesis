package com.example.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.constraintElements.FunctionApplication;
import com.example.constraintElements.FunctionSymbol;
import com.example.constraintElements.Term;
import com.example.constraintElements.variable;

import java.util.Arrays;

public class TermParser {

    public static Term parse(String input) {

        input = input.trim();

        if (input.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty.");
        }

        if (isVariable(input)) {
            return parseVariable(input);
        }

        else if (isFunctionSymbol(input)) {
            return parseFunctionApplication(input);
        }

        else {
            throw new IllegalArgumentException("Invalid input: " + input);
        }
    }

    private static boolean isVariable(String input) {
        return input.length() == 1 && Character.isUpperCase(input.charAt(0));
    }

    private static variable parseVariable(String input) {
        char name = input.charAt(0);
        return new variable(name);
    }

    private static boolean isFunctionSymbol(String input) {
        return input.matches("[a-z]_[ou](\\(.*\\))?");
    }

    private static FunctionApplication parseFunctionApplication(String input) {
        Pattern pattern = Pattern.compile("([a-z]_[ou])(\\((.*)\\))?");
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            String functionSymbolStr = matcher.group(1);
            FunctionSymbol functionSymbol = parseFunctionSymbol(functionSymbolStr);

            String argsStr = matcher.group(3);
            Term[] args = argsStr != null ? parseArguments(argsStr) : new Term[0];

            return new FunctionApplication(functionSymbol, args);
        } else {
            throw new IllegalArgumentException("Invalid function application: " + input);
        }
    }

    private static FunctionSymbol parseFunctionSymbol(String input) {
        char name = input.charAt(0);
        boolean isOrdered = input.endsWith("_o");
        return new FunctionSymbol(name, isOrdered);
    }

    private static Term[] parseArguments(String argsStr) {
        return Arrays.stream(argsStr.split(","))
                .map(String::trim)
                .map(TermParser::parse)
                .toArray(Term[]::new);
    }

    public static void main(String[] args) {
        try {
            String complexFunctionStr = "g_o(X, Y, f_o(Z), Z)";
            Term complexFunction = TermParser.parse(complexFunctionStr);
            System.out.println("Parsed Complex FunctionApplication: " + complexFunction);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
package com.example.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.constraintElements.FunctionApplication;
import com.example.constraintElements.FunctionSymbol;
import com.example.constraintElements.Term;
import com.example.constraintElements.variable;

import java.util.ArrayList;
import java.util.List;

public class TermParser {

    public static Term parse(String input) {
        input = input.trim();

        if (input.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty.");
        }

        if (isVariable(input)) {
            return parseVariable(input);
        } else if (isFunctionSymbol(input)) {
            return parseFunctionApplication(input);
        } else {
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

    // Updated parseArguments method to handle nested parentheses
    private static Term[] parseArguments(String argsStr) {
        List<String> argStrings = new ArrayList<>();
        int depth = 0;
        StringBuilder currentArg = new StringBuilder();

        for (char c : argsStr.toCharArray()) {
            if (c == '(') depth++;
            else if (c == ')') depth--;

            if (c == ',' && depth == 0) {
                argStrings.add(currentArg.toString().trim());
                currentArg.setLength(0);
            } else {
                currentArg.append(c);
            }
        }

        if (currentArg.length() > 0) {
            argStrings.add(currentArg.toString().trim());
        }

        return argStrings.stream()
                         .map(TermParser::parse)
                         .toArray(Term[]::new);
    }

}
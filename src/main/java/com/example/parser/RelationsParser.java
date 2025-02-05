package com.example.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.constraintElements.FunctionSymbol;

public class RelationsParser {

    public static void parse(String input) {
        input = input.trim();

        Pattern pattern = Pattern.compile("\\(([a-z]_[ou]),\\s*([a-z]_[ou]),\\s*([01]?\\.[0-9]+|[01])\\)");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String functionSymbol1Str = matcher.group(1);
            String functionSymbol2Str = matcher.group(2);
            double value = Double.parseDouble(matcher.group(3));

            FunctionSymbol functionSymbol1 = parseFunctionSymbol(functionSymbol1Str);
            FunctionSymbol functionSymbol2 = parseFunctionSymbol(functionSymbol2Str);

            com.example.Sim.relationCollection.add(functionSymbol1, functionSymbol2, value);
        }
    }

    private static FunctionSymbol parseFunctionSymbol(String input) {
        char name = input.charAt(0);
        boolean isOrdered = input.endsWith("_o");
        return new FunctionSymbol(name, isOrdered);
    }
}

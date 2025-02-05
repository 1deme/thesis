package com.example.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.constraintElements.FunctionApplication;
import com.example.constraintElements.FunctionSymbol;
import com.example.constraintElements.Term;

public class Permutations {

    public static Set<Character> allFunctionSymbols = new HashSet<>();

    public static char getFreshFunctionSymbol() {
        for (char c = 'a'; c <= 'z'; c++) {
            if (!allFunctionSymbols.contains(c)) {
                return c;
            }
        }
        throw new IllegalStateException("No fresh function symbol available.");
    }

    
    public static List<FunctionApplication> generateInstances(FunctionSymbol functionSymbol, Term[] args, int arity) {
        List<FunctionApplication> instances = new ArrayList<>();
        generateSubsets(args, arity, 0, new Term[arity], 0, instances, functionSymbol);
        return instances;
    }

    /**
     * Recursively generates all subsets of length `arity` from the `args` array.
     *
     * @param args           The array of arguments to choose subsets from.
     * @param arity          The length of the subsets to generate.
     * @param start          The starting index for generating subsets.
     * @param current        The current subset being constructed.
     * @param currentIndex   The current index in the `current` array.
     * @param instances      The list to store the generated FunctionApplication instances.
     * @param functionSymbol The FunctionSymbol to use in the FunctionApplication.
     */
    private static void generateSubsets(Term[] args, int arity, int start, Term[] current, int currentIndex, List<FunctionApplication> instances, FunctionSymbol functionSymbol) {
        // If the current subset has the desired length, create a FunctionApplication
        if (currentIndex == arity) {
            instances.add(new FunctionApplication(functionSymbol, current.clone()));
            return;
        }

        // Recursively generate subsets
        for (int i = start; i < args.length; i++) {
            current[currentIndex] = args[i]; // Include the current argument
            generateSubsets(args, arity, i + 1, current, currentIndex + 1, instances, functionSymbol);
        }
    }
}

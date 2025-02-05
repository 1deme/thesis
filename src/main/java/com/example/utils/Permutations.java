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

    
    public static List<FunctionApplication> generatePermutations(FunctionSymbol functionSymbol, Term[] args, int arity) {
        List<FunctionApplication> instances = new ArrayList<>();
        boolean[] used = new boolean[args.length]; // Track used elements
        backtrack(args, arity, used, new ArrayList<>(), instances, functionSymbol);
        return instances;
    }

    /**
     * Backtracking helper to generate permutations.
     */
    private static void backtrack(Term[] args, int arity, boolean[] used, List<Term> current, 
                                  List<FunctionApplication> instances, FunctionSymbol functionSymbol) {
        // Base case: If the current permutation is complete
        if (current.size() == arity) {
            Term[] permutation = current.toArray(new Term[0]);
            instances.add(new FunctionApplication(functionSymbol, permutation));
            return;
        }

        // Recursive case: Build permutations
        for (int i = 0; i < args.length; i++) {
            if (!used[i]) {
                used[i] = true; // Mark element as used
                current.add(args[i]); // Add to current permutation
                backtrack(args, arity, used, current, instances, functionSymbol);
                current.remove(current.size() - 1); // Remove last element (backtrack)
                used[i] = false; // Unmark element
            }
        }
    }
}

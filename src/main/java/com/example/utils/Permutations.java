package com.example.utils;

import java.util.ArrayList;
import java.util.List;

import com.example.constraintElements.FunctionApplication;
import com.example.constraintElements.FunctionSymbol;
import com.example.constraintElements.Term;

public class Permutations {

    public static char getFreshFunctionSymbol() {
        for (char c = 'a'; c <= 'z'; c++) {
            if (!com.example.Sim.allFunctionSymbols.contains(c)) {
                return c;
            }
        }
        throw new IllegalStateException("No fresh function symbol available.");
    }

    
    public static List<FunctionApplication> generateInstances(FunctionSymbol functionSymbol, Term[] args) {
        List<FunctionApplication> instances = new ArrayList<>();
        char freshFunctionSymbol = getFreshFunctionSymbol();
        generatePermutations(args, 0, instances, functionSymbol, freshFunctionSymbol);
        return instances;
    }

    private static void generatePermutations(Term[] args, int start, List<FunctionApplication> instances, FunctionSymbol functionSymbol, char freshFunctionSymbol) {
        if (start == args.length - 1) {
            // Add a new instance with the current permutation
            instances.add(new FunctionApplication(new FunctionSymbol(freshFunctionSymbol, true), args.clone()));
            return;
        }
        for (int i = start; i < args.length; i++) {
            swap(args, i, start);
            generatePermutations(args, start + 1, instances, functionSymbol, freshFunctionSymbol);
            swap(args, i, start); // backtrack
        }
    }

    private static void swap(Term[] args, int i, int j) {
        Term temp = args[i];
        args[i] = args[j];
        args[j] = temp;
    }

}

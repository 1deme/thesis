package com.example.utils;

import java.util.ArrayList;
import java.util.List;

import com.example.constraintElements.FunctionApplication;
import com.example.constraintElements.FunctionSymbol;
import com.example.constraintElements.Term;

public class Permutations {

    
    public static List<FunctionApplication> generateInstances(FunctionSymbol functionSymbol, Term[] args) {
        List<FunctionApplication> instances = new ArrayList<>();
        generatePermutations(args, 0, instances, functionSymbol);
        return instances;
    }

    private static void generatePermutations(Term[] args, int start, List<FunctionApplication> instances, FunctionSymbol functionSymbol) {
        if (start == args.length - 1) {
            // Add a new instance with the current permutation
            instances.add(new FunctionApplication(functionSymbol, args.clone(), false));
            return;
        }
        for (int i = start; i < args.length; i++) {
            swap(args, i, start);
            generatePermutations(args, start + 1, instances, functionSymbol);
            swap(args, i, start); // backtrack
        }
    }

    private static void swap(Term[] args, int i, int j) {
        Term temp = args[i];
        args[i] = args[j];
        args[j] = temp;
    }

}

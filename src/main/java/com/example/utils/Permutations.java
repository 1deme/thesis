package com.example.utils;

import java.util.ArrayList;
import java.util.List;

import com.example.constraintElements.FunctionApplication;
import com.example.constraintElements.FunctionSymbol;
import com.example.constraintElements.Term;

public class Permutations {
    
    public static List<FunctionApplication> generatePermutations(FunctionSymbol functionSymbol, Term[] args, int arity) {
        List<FunctionApplication> instances = new ArrayList<>();
        boolean[] used = new boolean[args.length];
        backtrack(args, arity, used, new ArrayList<>(), instances, functionSymbol);
        return instances;
    }

    private static void backtrack(Term[] args, int arity, boolean[] used, List<Term> current, 
        List<FunctionApplication> instances, FunctionSymbol functionSymbol) {

        if (current.size() == arity) {
            Term[] permutation = current.toArray(new Term[0]);
            instances.add(new FunctionApplication(functionSymbol, permutation));
            return;
        }

        for (int i = 0; i < args.length; i++) {
            if (!used[i]) {
                used[i] = true;
                current.add(args[i]);
                backtrack(args, arity, used, current, instances, functionSymbol);
                current.remove(current.size() - 1);
                used[i] = false;
            }
        }
    }
}

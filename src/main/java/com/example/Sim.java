package com.example;
import java.util.List;
import java.util.Set;

import com.example.constraintElements.FunctionApplication;
import com.example.constraintElements.variable;
import com.example.dnf.Conjunction;
import com.example.dnf.Disjunction;
import com.example.predicates.SimilarityPredicate;
import com.example.relations.relationCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Sim {

    public static Disjunction disjunction = new Disjunction(new ArrayList<>());
    public static relationCollection relationCollection = new relationCollection();
    public static Set<Character> allFunctionSymbols = new HashSet<>();


    public static void solve(){
        while(disjunction.conjunctions.size() != 0){
            sim(disjunction.conjunctions.remove(0));
        }
    }

    public static boolean sim(Conjunction conjunction){
        
        while(conjunction.constraints.size() != 0){

            System.out.println(conjunction);

            SimilarityPredicate similarityPredicate = conjunction.constraints.remove(0);

            if(delVarCond(similarityPredicate)){
                continue;
            }
            if(decOfSCond(similarityPredicate)){
                decOfSOp((FunctionApplication) similarityPredicate.el1, (FunctionApplication) similarityPredicate.el2, similarityPredicate.CutValue, conjunction);
                continue;
            }
            if(decUFSCond(similarityPredicate)){
                decUFSOp(similarityPredicate, conjunction);
                continue;
            }
            if(oriUFSCond(similarityPredicate)){
                oriUFSOp(similarityPredicate, conjunction);
                continue;
            }
            if(oriVarCond(similarityPredicate)){
                oriVarOp(similarityPredicate, conjunction);
                continue;
            }
            if(SolSUCond(similarityPredicate)){
                SolSUOp(similarityPredicate, conjunction);
                continue;
            }
            if(ConfFSCond(similarityPredicate) || ConfUFSCond(similarityPredicate) || 
                ConfOFSCond(similarityPredicate) || CheckOccCond(similarityPredicate) )
            {
                return false;
            }
            throw new UnsupportedOperationException("Pattern not recognized");
        }
        System.out.println(conjunction.solution + " " + conjunction.proximtyDegree);
        return true;
    }

    private static boolean delVarCond(SimilarityPredicate similarityPredicate) {
        return similarityPredicate.el1.isVariable() && similarityPredicate.el1.equals(similarityPredicate.el2);
    }

    private static boolean decOfSCond(SimilarityPredicate similarityPredicate) {
        return 
            similarityPredicate.el1 instanceof FunctionApplication &&
            similarityPredicate.el2 instanceof FunctionApplication &&
            similarityPredicate.el1.isOrdered() &&
            similarityPredicate.el2.isOrdered() &&
            similarityPredicate.el1.arity() == similarityPredicate.el2.arity() &&
            com.example.relations.relationCollection.lookup(similarityPredicate.el1, similarityPredicate.el2) >= similarityPredicate.CutValue;
    }

    private static boolean decUFSCond(SimilarityPredicate similarityPredicate) {
        return 
            similarityPredicate.el1 instanceof FunctionApplication &&
            similarityPredicate.el2 instanceof FunctionApplication &&
            !similarityPredicate.el1.isOrdered() &&
            !similarityPredicate.el2.isOrdered() &&
            com.example.relations.relationCollection.lookup(similarityPredicate.el1, similarityPredicate.el2) >= similarityPredicate.CutValue;
    }

    private static boolean oriUFSCond(SimilarityPredicate similarityPredicate){
        return 
            similarityPredicate.el1 instanceof FunctionApplication &&
            similarityPredicate.el2 instanceof FunctionApplication &&
            !similarityPredicate.el1.isOrdered() &&
            !similarityPredicate.el2.isOrdered() &&
            similarityPredicate.el1.arity() < similarityPredicate.el2.arity();
    }

    public static boolean oriVarCond(SimilarityPredicate similarityPredicate){
        return !similarityPredicate.el1.isVariable() && similarityPredicate.el2.isVariable();
    }

    public static boolean ConfFSCond(SimilarityPredicate similarityPredicate){
        return 
            similarityPredicate.el1 instanceof FunctionApplication &&
            similarityPredicate.el2 instanceof FunctionApplication &&
            (
                (similarityPredicate.el1.isOrdered() && !similarityPredicate.el2.isOrdered()) ||  
                (!similarityPredicate.el1.isOrdered() && similarityPredicate.el2.isOrdered())
            );
    }

    public static boolean ConfUFSCond(SimilarityPredicate similarityPredicate){
        return 
            similarityPredicate.el1 instanceof FunctionApplication &&
            similarityPredicate.el2 instanceof FunctionApplication &&
            !similarityPredicate.el1.isOrdered() &&
            !similarityPredicate.el2.isOrdered() &&
            com.example.relations.relationCollection.lookup(similarityPredicate.el1, similarityPredicate.el2) < similarityPredicate.CutValue;
    }

    public static boolean ConfOFSCond(SimilarityPredicate similarityPredicate){
        return 
            similarityPredicate.el1 instanceof FunctionApplication &&
            similarityPredicate.el2 instanceof FunctionApplication &&
            similarityPredicate.el1.isOrdered() &&
            similarityPredicate.el2.isOrdered() &&
            (
                similarityPredicate.el1.arity() != similarityPredicate.el2.arity() ||
                com.example.relations.relationCollection.lookup(similarityPredicate.el1, similarityPredicate.el2) < similarityPredicate.CutValue
            );
    }

    public static boolean CheckOccCond(SimilarityPredicate similarityPredicate){
        return
            similarityPredicate.el1.isVariable() &&
            !similarityPredicate.el2.equals(similarityPredicate.el1) &&
            similarityPredicate.el2.contains(similarityPredicate.el1);
    }

    public static boolean SolSUCond(SimilarityPredicate similarityPredicate){
        return 
            similarityPredicate.el1.isVariable() &&
            !similarityPredicate.el1.equals(similarityPredicate.el2) &&
            !similarityPredicate.el2.contains(similarityPredicate.el1);
    }

    public static void oriVarOp(SimilarityPredicate similarityPredicate, Conjunction conjunction){
        conjunction.constraints.add(
            new SimilarityPredicate(similarityPredicate.el2, similarityPredicate.el1, similarityPredicate.CutValue)
        );
    }

    public static void decOfSOp(FunctionApplication f1, FunctionApplication f2, double cutVal, Conjunction conjunction){
        for(int i = 0; i < f1.arity(); i++){
            conjunction.constraints.add(
                new SimilarityPredicate(f1.args[i], f2.args[i], cutVal)
            );
        }
        conjunction.proximtyDegree = Math.min(
            conjunction.proximtyDegree,
            com.example.relations.relationCollection.lookup(f1, f2)
        );
    }

    public static void decUFSOp(SimilarityPredicate similarityPredicate, Conjunction conjunction){
        FunctionApplication f1 = (FunctionApplication) similarityPredicate.el1;
        FunctionApplication f2 = (FunctionApplication) similarityPredicate.el2;
        List<FunctionApplication> prems = com.example.utils.Permutations.generateInstances(f1.functionSymbol, Arrays.copyOf(f1.args, f2.arity()));
        FunctionApplication mem = prems.remove(0);
        for(FunctionApplication prem : prems){
            Conjunction conj = conjunction.createCopy();
            decOfSOp(
                (FunctionApplication) prem,
                (FunctionApplication) f2, 
                similarityPredicate.CutValue, conj);
            disjunction.add(conj);
        }
        decOfSOp(mem, f2, similarityPredicate.CutValue, conjunction);
        

    }

    public static void oriUFSOp(SimilarityPredicate similarityPredicate, Conjunction conjunction){
        conjunction.constraints.add(
            new SimilarityPredicate(similarityPredicate.el2, similarityPredicate.el1, similarityPredicate.CutValue)
        );
    }

    public static void SolSUOp(SimilarityPredicate similarityPredicate, Conjunction conjunction){
        conjunction.solution.add(similarityPredicate.el1.toString() + " -> " + similarityPredicate.el2.toString());
        conjunction.map((variable) similarityPredicate.el1, similarityPredicate.el2);
    }
}
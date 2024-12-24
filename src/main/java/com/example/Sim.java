package com.example;
import java.util.List;
import java.util.function.Predicate;

import com.example.constraintElements.FunctionApplication;
import com.example.constraintElements.FunctionSymbol;
import com.example.constraintElements.Term;
import com.example.constraintElements.TermVariable;
import com.example.dnf.Conjunction;
import com.example.dnf.Disjunction;
import com.example.predicates.SimilarityPredicate;
import com.example.relations.relationCollection;
import java.util.ArrayList;

public class Sim {

    static Disjunction disjunction = new Disjunction(new ArrayList<>());
    static relationCollection relationCollection = new relationCollection();

    public static boolean solve(){
        for(Conjunction d : disjunction.conjunctions){
            if(sim(d)){
                return true;
            }
        }
        return false;
    }

    public static boolean sim(Conjunction conjunction){
        
        for(int i = 0; i < conjunction.constraints.size(); i++){

            SimilarityPredicate similarityPredicate = conjunction.constraints.remove(0);
            if(similarityPredicate.isSolved){ 
                conjunction.constraints.add(conjunction.constraints.remove(0));
                continue;
            }
            if(delSimCond(similarityPredicate)){
                i = -1;
                continue;
            }
            if(decUFSCond(similarityPredicate)){
                decUFSOp(similarityPredicate, conjunction);
                i = -1;
                continue;
            }
            if(decEqCond(similarityPredicate)){
                decSimOp((FunctionApplication) similarityPredicate.el1, (FunctionApplication) similarityPredicate.el2, similarityPredicate.RelationId, similarityPredicate.CutValue, conjunction.constraints);
                i = -1;
                continue;
            }
            if(oriEqCond(similarityPredicate)){
                oriSimOp(similarityPredicate, conjunction.constraints);
                i = -1;
                continue;
            }
            if(elimSimCond(similarityPredicate, conjunction.constraints)){
                elimSimOp(similarityPredicate, conjunction);
                i = -1;
                continue;
            }
            if(conflSimCond(similarityPredicate) || mismEqCond(similarityPredicate) || occEqCond(similarityPredicate)){
                conjunction.constraints.clear();
                return false;
            }
            conjunction.constraints.add(similarityPredicate);
        
    
        }
        return true;
    }

    public static boolean decUFSCond(SimilarityPredicate similarityPredicate){
        if(similarityPredicate.el1 instanceof FunctionApplication && similarityPredicate.el2 instanceof FunctionApplication){
            FunctionApplication f1 = (FunctionApplication) similarityPredicate.el1;
            FunctionApplication f2 = (FunctionApplication) similarityPredicate.el2;
            return f1.args.length == f2.args.length && (!f1.isOrdered() || !f2.isOrdered());
        } 
        return false;
    }

    public static void decUFSOp(SimilarityPredicate similarityPredicate, Conjunction conjunction){
        FunctionApplication f1 = (FunctionApplication) similarityPredicate.el1;
        FunctionApplication f2 = (FunctionApplication) similarityPredicate.el2;
        if(!f1.isOrdered()){
            List<FunctionApplication> prems = generateInstances(f1.functionSymbol, f1.args);
            for(FunctionApplication prem : prems){
                Conjunction conj = conjunction.createCopy();
                decSimOp(
                    (FunctionApplication) prem,
                    (FunctionApplication) f2, 
                    similarityPredicate.RelationId,
                    similarityPredicate.CutValue, conj.constraints);
                disjunction.add(conj);
            }
        }
        else{
            List<FunctionApplication> prems = generateInstances(f2.functionSymbol, f2.args);
            for(FunctionApplication prem : prems){
                decSimOp(
                    (FunctionApplication) f1,
                    (FunctionApplication) prem, 
                    similarityPredicate.RelationId,
                    similarityPredicate.CutValue, conjunction.constraints);

            }
        }
    }

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


    public static boolean occEqCond(SimilarityPredicate similarityPredicate){
        return !(similarityPredicate.el1 instanceof TermVariable) 
            && similarityPredicate.el1 != similarityPredicate.el2
            && similarityPredicate.el2.contains(similarityPredicate.el1);
    }

    public static boolean mismEqCond(SimilarityPredicate similarityPredicate){
        return (similarityPredicate.el1 instanceof FunctionApplication 
            && similarityPredicate.el2 instanceof FunctionApplication) 
            && ((FunctionApplication )similarityPredicate.el1).args.length != ((FunctionApplication )similarityPredicate.el2).args.length;   
    }


    public static boolean decEqCond(SimilarityPredicate similarityPredicate){
        return similarityPredicate.el1 instanceof FunctionApplication 
            && similarityPredicate.el2 instanceof FunctionApplication 
            && ((FunctionApplication) similarityPredicate.el1).args.length == ((FunctionApplication) similarityPredicate.el2).args.length;
    }

    public static boolean oriEqCond(SimilarityPredicate similarityPredicate){
        return similarityPredicate.el2.isVariable() && !similarityPredicate.el1.isVariable();
    }

    private static boolean delSimCond(SimilarityPredicate similarityPredicate) {
        return delEqCond(similarityPredicate) 
                 && com.example.relations.relationCollection.lookup(similarityPredicate.el1, similarityPredicate.el2, similarityPredicate.RelationId) >= similarityPredicate.CutValue;
    }

    private static void decSimOp(FunctionApplication f1, FunctionApplication f2, int relId, double cutVal, List<SimilarityPredicate> conjunction) {
         for(int i = f1.args.length - 1; i >= 0; i--){
            conjunction.add(new SimilarityPredicate(f1.args[i], f2.args[i], relId, cutVal));
        }
        conjunction.add(new SimilarityPredicate(f1.functionSymbol, f2.functionSymbol, relId, cutVal));
    }

    private static void oriSimOp(SimilarityPredicate similarityPredicate, List<SimilarityPredicate> conjunction) {
        conjunction.add(
            new SimilarityPredicate(similarityPredicate.el1, similarityPredicate.el2, similarityPredicate.RelationId, similarityPredicate.CutValue)
        );
    }

    private static boolean elimSimCond(SimilarityPredicate similarityPredicate, List<SimilarityPredicate> conjunction) {
        boolean notInEl2 = similarityPredicate.el2.contains(similarityPredicate.el1);
        if(notInEl2 == true){
            return false;
        }
        for(SimilarityPredicate pc : conjunction){
            if(pc instanceof SimilarityPredicate){
                SimilarityPredicate sp = (SimilarityPredicate) pc;
                if(
                    sp.RelationId == similarityPredicate.RelationId &&
                    sp.CutValue == similarityPredicate.CutValue &&
                    (pc.el1.contains(similarityPredicate.el1) || pc.el2.contains(similarityPredicate.el1))){
                    return true;
                }
            }
            
        }
        return false;
    }
    
    private static void elimSimOp(SimilarityPredicate similarityPredicate, Conjunction conjunction) {
        Predicate<SimilarityPredicate> p = x -> x instanceof SimilarityPredicate && ((SimilarityPredicate)x).CutValue == similarityPredicate.CutValue && ((SimilarityPredicate)x).RelationId == similarityPredicate.RelationId;
        conjunction.map(similarityPredicate.el1, similarityPredicate.el2, p);
        conjunction.constraints.add(similarityPredicate);
    }

    public static boolean delEqCond(SimilarityPredicate similarityPredicate){
        return similarityPredicate.el1.isAtomic() && similarityPredicate.el1.equals(similarityPredicate.el2);
    }

    private static boolean conflSimCond(SimilarityPredicate similarityPredicate) {
        return delEqCond(similarityPredicate) &&
        com.example.relations.relationCollection.lookup(similarityPredicate.el1, similarityPredicate.el2, similarityPredicate.RelationId) < similarityPredicate.CutValue;
    }

}

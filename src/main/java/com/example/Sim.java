package com.example;
import java.util.List;
import java.util.function.Predicate;

import com.example.constraintElements.FunctionApplication;
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
        while(disjunction.conjunctions.size() != 0){
            if(sim(disjunction.conjunctions.remove(0))){
                return true;
            }
        }
        return false;
    }

    public static boolean sim(Conjunction conjunction){
        
        while(conjunction.constraints.size() != 0){

            SimilarityPredicate similarityPredicate = conjunction.constraints.remove(0);

            if(delSimCond(similarityPredicate)){
                continue;
            }
            if(decUFSCond(similarityPredicate)){
                decUFSOp(similarityPredicate, conjunction);
                continue;
            }
            if(decSimCond(similarityPredicate)){
                decSimOp((FunctionApplication) similarityPredicate.el1, (FunctionApplication) similarityPredicate.el2, similarityPredicate.RelationId, similarityPredicate.CutValue, conjunction.constraints);
                continue;
            }
            if(oriSimCond(similarityPredicate)){
                oriSimOp(similarityPredicate, conjunction.constraints);
                continue;
            }
            if(elimSimCond(similarityPredicate, conjunction.constraints)){
                elimSimOp(similarityPredicate, conjunction);
                continue;
            }
            if(conflSimCond(similarityPredicate) || mismSimCond(similarityPredicate) || occSimCond(similarityPredicate)){
                conjunction.constraints.clear();
                return false;
            }
        }
        return true;
    }

    public static boolean decUFSCond(SimilarityPredicate similarityPredicate){
        if(similarityPredicate.el1 instanceof FunctionApplication && similarityPredicate.el2 instanceof FunctionApplication){
            FunctionApplication f1 = (FunctionApplication) similarityPredicate.el1;
            FunctionApplication f2 = (FunctionApplication) similarityPredicate.el2;
            return !f1.isAtomic() && f1.args.length == f2.args.length && (!f1.isOrdered() || !f2.isOrdered());
        } 
        return false;
    }

    public static boolean occSimCond(SimilarityPredicate similarityPredicate){
        return !(similarityPredicate.el1 instanceof TermVariable) 
            && similarityPredicate.el1 != similarityPredicate.el2
            && similarityPredicate.el2.contains(similarityPredicate.el1);
    }

    public static boolean mismSimCond(SimilarityPredicate similarityPredicate){
        return (similarityPredicate.el1 instanceof FunctionApplication 
            && similarityPredicate.el2 instanceof FunctionApplication) 
            && ((FunctionApplication )similarityPredicate.el1).args.length != ((FunctionApplication )similarityPredicate.el2).args.length;   
    }

    public static boolean decSimCond(SimilarityPredicate similarityPredicate){
        return
            similarityPredicate.el1 instanceof FunctionApplication &&
            similarityPredicate.el2 instanceof FunctionApplication &&
            !similarityPredicate.el1.isAtomic() &&
            ((FunctionApplication) similarityPredicate.el1).args.length == ((FunctionApplication) similarityPredicate.el2).args.length;
    }

    public static boolean oriSimCond(SimilarityPredicate similarityPredicate){
        return similarityPredicate.el2.isVariable() && !similarityPredicate.el1.isVariable();
    }

    private static boolean delSimCond(SimilarityPredicate similarityPredicate) {
        return 
            similarityPredicate.el1.isAtomic() &&
            similarityPredicate.el2.isAtomic() &&
            com.example.relations.relationCollection.lookup(similarityPredicate.el1, similarityPredicate.el2, similarityPredicate.RelationId) >= similarityPredicate.CutValue;
    }

    private static boolean conflSimCond(SimilarityPredicate similarityPredicate) {
        return 
            similarityPredicate.el1.isAtomic() && 
            similarityPredicate.el2.isAtomic() &&
            com.example.relations.relationCollection.lookup(similarityPredicate.el1, similarityPredicate.el2, similarityPredicate.RelationId) < similarityPredicate.CutValue;
    }

    private static boolean elimSimCond(SimilarityPredicate similarityPredicate, List<SimilarityPredicate> conjunction) {
        return !similarityPredicate.el2.contains(similarityPredicate.el1);
        // boolean notInEl2 = similarityPredicate.el2.contains(similarityPredicate.el1);
        // if(notInEl2){
        //     return false;
        // }
        // for(SimilarityPredicate pc : conjunction){
        //     if(
        //         pc.RelationId == similarityPredicate.RelationId &&
        //         pc.CutValue == similarityPredicate.CutValue &&
        //         (pc.el1.contains(similarityPredicate.el1) || pc.el2.contains(similarityPredicate.el1))){
        //             return true;
        //     }
        // }
        // return false;
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
    
    private static void elimSimOp(SimilarityPredicate similarityPredicate, Conjunction conjunction) {
        Predicate<SimilarityPredicate> p = x -> x instanceof SimilarityPredicate && ((SimilarityPredicate)x).CutValue == similarityPredicate.CutValue && ((SimilarityPredicate)x).RelationId == similarityPredicate.RelationId;
        System.out.println(similarityPredicate.el1 + "->" + similarityPredicate.el2);
        conjunction.constraints.add(similarityPredicate);
        conjunction.map(similarityPredicate.el1, similarityPredicate.el2, p);
        
    }


    public static void decUFSOp(SimilarityPredicate similarityPredicate, Conjunction conjunction){
        FunctionApplication f1 = (FunctionApplication) similarityPredicate.el1;
        FunctionApplication f2 = (FunctionApplication) similarityPredicate.el2;
        if(!f1.isOrdered()){
            List<FunctionApplication> prems = com.example.utils.Permutations.generateInstances(f1.functionSymbol, f1.args);
            FunctionApplication mem = prems.remove(0);
            for(FunctionApplication prem : prems){
                Conjunction conj = conjunction.createCopy();
                decSimOp(
                    (FunctionApplication) prem,
                    (FunctionApplication) f2, 
                    similarityPredicate.RelationId,
                    similarityPredicate.CutValue, conj.constraints);
                disjunction.add(conj);
            }
            decSimOp(mem, f2, similarityPredicate.RelationId, similarityPredicate.CutValue, conjunction.constraints);
        }
        else{
            List<FunctionApplication> prems = com.example.utils.Permutations.generateInstances(f2.functionSymbol, f2.args);
            FunctionApplication mem = prems.remove(0);
            for(FunctionApplication prem : prems){
                Conjunction conj = conjunction.createCopy();
                decSimOp(
                    (FunctionApplication) f1,
                    (FunctionApplication) prem, 
                    similarityPredicate.RelationId,
                    similarityPredicate.CutValue, conj.constraints);
                    disjunction.add(conj);
            }
            decSimOp(mem, f2, similarityPredicate.RelationId, similarityPredicate.CutValue, conjunction.constraints);
        }
    }

}

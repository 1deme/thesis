package com.example;
import java.util.List;
import java.util.function.Predicate;

import com.example.constraintElements.FunctionApplication;
import com.example.constraintElements.TermVariable;
import com.example.dnf.Conjunction;
import com.example.predicates.PrimitiveConstraint;
import com.example.predicates.SimilarityPredicate;
import com.example.relations.relationCollection;

public class Sim {

    static relationCollection relationCollection = new relationCollection();

    public static boolean sim(Conjunction conjunction){
        
        for(int i = 0; i < conjunction.constraints.size(); i++){
            if(conjunction.constraints.get(0).isSolved){ 
                conjunction.constraints.add(conjunction.constraints.remove(0));
                continue;
            }
            if(conjunction.constraints.get(0) instanceof SimilarityPredicate){
                SimilarityPredicate similarityPredicate = (SimilarityPredicate) conjunction.constraints.remove(0);
                if(delSimCond(similarityPredicate)){
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
    
        }
        return true;
    }

    public static boolean occEqCond(PrimitiveConstraint primitiveConstraint){
        return !(primitiveConstraint.el1 instanceof TermVariable) 
            && primitiveConstraint.el1 != primitiveConstraint.el2
            && primitiveConstraint.el2.contains(primitiveConstraint.el1);
    }

    public static boolean mismEqCond(PrimitiveConstraint primitiveConstraint){
        return (primitiveConstraint.el1 instanceof FunctionApplication 
            && primitiveConstraint.el2 instanceof FunctionApplication) 
            && ((FunctionApplication )primitiveConstraint.el1).args.length != ((FunctionApplication )primitiveConstraint.el2).args.length;   
    }


    public static boolean decEqCond(PrimitiveConstraint primitiveConstraint){
        return primitiveConstraint.el1 instanceof FunctionApplication 
            && primitiveConstraint.el2 instanceof FunctionApplication 
            && ((FunctionApplication) primitiveConstraint.el1).args.length == ((FunctionApplication) primitiveConstraint.el2).args.length;
    }

    public static boolean oriEqCond(PrimitiveConstraint primitiveConstraint){
        return primitiveConstraint.el2.isVariable() && !primitiveConstraint.el1.isVariable();
    }

    private static boolean delSimCond(SimilarityPredicate similarityPredicate) {
        return delEqCond(similarityPredicate) 
                 && com.example.relations.relationCollection.lookup(similarityPredicate.el1, similarityPredicate.el2, similarityPredicate.RelationId) >= similarityPredicate.CutValue;
    }

    private static void decSimOp(FunctionApplication f1, FunctionApplication f2, int relId, double cutVal, List<PrimitiveConstraint> conjunction) {
         for(int i = f1.args.length - 1; i >= 0; i--){
            conjunction.add(new SimilarityPredicate(f1.args[i], f2.args[i], relId, cutVal));
        }
        conjunction.add(new SimilarityPredicate(f1.functionSymbol, f2.functionSymbol, relId, cutVal));
    }

    private static void oriSimOp(SimilarityPredicate similarityPredicate, List<PrimitiveConstraint> conjunction) {
        conjunction.add(
            new SimilarityPredicate(similarityPredicate.el1, similarityPredicate.el2, similarityPredicate.RelationId, similarityPredicate.CutValue)
        );
    }

    private static boolean elimSimCond(SimilarityPredicate similarityPredicate, List<PrimitiveConstraint> conjunction) {
        boolean notInEl2 = similarityPredicate.el2.contains(similarityPredicate.el1);
        if(notInEl2 == true){
            return false;
        }
        for(PrimitiveConstraint pc : conjunction){
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
        Predicate<PrimitiveConstraint> p = x -> x instanceof SimilarityPredicate && ((SimilarityPredicate)x).CutValue == similarityPredicate.CutValue && ((SimilarityPredicate)x).RelationId == similarityPredicate.RelationId;
        conjunction.map(similarityPredicate.el1, similarityPredicate.el2, p);
        conjunction.constraints.add(similarityPredicate);
    }

    public static boolean delEqCond(PrimitiveConstraint primitiveConstraint){
        return primitiveConstraint.el1.isAtomic() && primitiveConstraint.el1.equals(primitiveConstraint.el2);
    }

    private static boolean conflSimCond(SimilarityPredicate similarityPredicate) {
        return delEqCond(similarityPredicate) &&
        com.example.relations.relationCollection.lookup(similarityPredicate.el1, similarityPredicate.el2, similarityPredicate.RelationId) < similarityPredicate.CutValue;
    }

}

package com.example.algorithm;

import java.util.LinkedList;
import java.util.List;

import com.example.constraintElements.FunctionApplication;
import com.example.constraintElements.FunctionSymbol;
import com.example.constraintElements.Term;
import com.example.constraintElements.variable;
import com.example.dnf.Conjunction;
import com.example.predicates.SimilarityPredicate;

public class SolPc implements SolTransformation {
    @Override
    public void apply(SimilarityPredicate similarityPredicate, Conjunction conjunction) {
        if(similarityPredicate.el2.isVariable()){
            return;
        }
        List<FunctionSymbol> neighbors =
         com.example.relations.relationCollection.getneighborhood(((FunctionApplication)similarityPredicate.el2).functionSymbol, similarityPredicate.CutValue);
        for (FunctionSymbol neighbor : neighbors) {

            double newCutVal = Math.min(
                similarityPredicate.CutValue, 
                com.example.relations.relationCollection.lookup(neighbor, ((FunctionApplication) similarityPredicate.el2).functionSymbol)
            );

            Term newTerm = constructNewTerm(neighbor, similarityPredicate.el2.arity());
            Conjunction newConjunction = conjunction.createCopy();
            newConjunction.map((variable) similarityPredicate.el1, newTerm);

            //conjunction.solution.add(new SimilarityPredicate(similarityPredicate.el1, newTerm, newCutVal));
            newConjunction.solution = new LinkedList<>(conjunction.solution);
            newConjunction.solution.add(new SimilarityPredicate(similarityPredicate.el1, newTerm, newCutVal));

            newConjunction.add(new SimilarityPredicate(newTerm, similarityPredicate.el2, newCutVal));
            com.example.algorithm.SolveSim.disjunction.add(newConjunction);
        }
        conjunction.solution.clear();
        conjunction.constraints.clear();
    }

    static Term constructNewTerm(FunctionSymbol neighbor, int arity){
        Term[] args = new Term[arity];
        for(int i = 0; i < arity; i++){
            args[i] = new variable(com.example.utils.FreshSymbolGenerator.generateFreshVariable());
        }
        return new FunctionApplication(neighbor, args);
    }
}

package com.example.algorithm;

import com.example.constraintElements.variable;
import com.example.dnf.Conjunction;
import com.example.predicates.SimilarityPredicate;

public class SolSc implements SolTransformation {
    @Override
    public void apply(SimilarityPredicate similarityPredicate, Conjunction conjunction) {
        conjunction.solution.add(similarityPredicate);
        conjunction.map((variable) similarityPredicate.el1, similarityPredicate.el2);
    }
}


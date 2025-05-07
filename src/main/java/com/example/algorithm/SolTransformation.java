package com.example.algorithm;

import com.example.dnf.Conjunction;
import com.example.predicates.SimilarityPredicate;

@FunctionalInterface
public interface SolTransformation {
    void apply(SimilarityPredicate similarityPredicate, Conjunction conjunction);
}

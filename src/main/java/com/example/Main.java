package com.example;

import java.util.ArrayList;

import com.example.constraintElements.FunctionApplication;
import com.example.constraintElements.Term;
import com.example.constraintElements.variable;
import com.example.dnf.Conjunction;
import com.example.predicates.SimilarityPredicate;
import com.example.relations.relationCollection;


public class Main {
    public static void main(String[] args) {
        
        variable x = new variable('x');
        variable y = new variable('y');
        variable z = new variable('z');

        FunctionApplication f = new FunctionApplication('f', true, new Term[]{x, y});
        FunctionApplication g = new FunctionApplication('g', true, new Term[]{f});

        FunctionApplication h = new FunctionApplication('h', true, new Term[]{z});

        SimilarityPredicate sim1 = new SimilarityPredicate(g, h, 0.1);

        relationCollection.add(g.functionSymbol, h.functionSymbol, 0.2);

        Conjunction conjunction = new Conjunction(new ArrayList<>());
        conjunction.add(sim1);

        com.example.Sim.disjunction.add(conjunction);
        com.example.Sim.solve();

    }
}
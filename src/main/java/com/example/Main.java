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
        FunctionApplication m = new FunctionApplication('m', true, new Term[]{});

        FunctionApplication f = new FunctionApplication('f', false, new Term[]{x, y});
        FunctionApplication g = new FunctionApplication('g', false, new Term[]{x, m});

        SimilarityPredicate sim1 = new SimilarityPredicate(f, g, 0.1);

        relationCollection.add(f, g, 0.2);


        Conjunction conjunction = new Conjunction(new ArrayList<>());
        conjunction.add(sim1);
        System.out.println(com.example.Sim.sim(conjunction));

    }
}
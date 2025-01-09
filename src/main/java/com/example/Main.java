package com.example;

import java.util.ArrayList;
import com.example.constraintElements.variable;
import com.example.dnf.Conjunction;
import com.example.predicates.SimilarityPredicate;


public class Main {
    public static void main(String[] args) {
        
        variable x = new variable('x');
        variable y = new variable('y');

        SimilarityPredicate sim1 = new SimilarityPredicate(x, y, 0);

        Conjunction conjunction = new Conjunction(new ArrayList<>());
        conjunction.add(sim1);

        com.example.Sim.sim(conjunction);

    }
}
package com.example;

import java.util.ArrayList;
import java.util.List;

import com.example.constraintElements.FunctionApplication;
import com.example.constraintElements.FunctionSymbol;
import com.example.constraintElements.Term;
import com.example.constraintElements.variable;
import com.example.dnf.Conjunction;
import com.example.dnf.Disjunction;
import com.example.predicates.SimilarityPredicate;
import com.example.relations.relationCollection;


public class Main {
    public static void main(String[] args) {
        // FunctionSymbol f = new FunctionSymbol('f');
        // FunctionSymbol g = new FunctionSymbol('g');
        // variable x = new variable('x');
        // variable y = new variable('y');
        // ordered b = new ordered('b');
        // ordered c = new ordered('c');
        // FunctionApplication bTerm = new FunctionApplication(b, new Term[]{}, true);
        // FunctionApplication cTerm = new FunctionApplication(c, new Term[]{}, true);
        

        // FunctionApplication fxy = new FunctionApplication(f, new Term[]{x, y}, false);
        // FunctionApplication gbc = new FunctionApplication(g, new Term[]{bTerm, cTerm}, false);
        // SimilarityPredicate sim = new SimilarityPredicate(fxy, gbc, 1, 0.7);

        // relationCollection.add(f, g, 1, 0.8);

        // List<SimilarityPredicate> pc = new ArrayList<>();
        // pc.add(sim);
        
        // Conjunction cc = new Conjunction(pc);
        
        // Disjunction disjunction = new Disjunction(new ArrayList<>());
        // disjunction.add(cc);
        // Sim.disjunction = disjunction;
    
        Sim.solve();




    }
}
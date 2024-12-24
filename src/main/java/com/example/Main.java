package com.example;

import java.util.ArrayList;
import java.util.List;

import com.example.constraintElements.FunctionApplication;
import com.example.constraintElements.FunctionConstant;
import com.example.constraintElements.FunctionSymbol;
import com.example.constraintElements.FunctionVariable;
import com.example.constraintElements.Term;
import com.example.constraintElements.TermVariable;
import com.example.dnf.Conjunction;
import com.example.predicates.PrimitiveConstraint;
import com.example.predicates.SimilarityPredicate;
import com.example.relations.relationCollection;


public class Main {
    public static void main(String[] args) {
        FunctionSymbol f = new FunctionSymbol('f');
        FunctionSymbol g = new FunctionSymbol('g');
        TermVariable x = new TermVariable('x');
        TermVariable y = new TermVariable('y');
        FunctionConstant b = new FunctionConstant('b');
        FunctionConstant c = new FunctionConstant('c');
        FunctionApplication bTerm = new FunctionApplication(b, new Term[]{}, true);
        FunctionApplication cTerm = new FunctionApplication(c, new Term[]{}, true);
        

        FunctionApplication fxy = new FunctionApplication(f, new Term[]{x, y}, false);
        FunctionApplication gbc = new FunctionApplication(g, new Term[]{bTerm, cTerm}, false);
        SimilarityPredicate sim = new SimilarityPredicate(fxy, gbc, 1, 0.8);

        relationCollection.add(f, g, 1, 0.7);

        List<SimilarityPredicate> pc = new ArrayList<>();
        pc.add(sim);
        
        Conjunction cc = new Conjunction(pc);
        Sim.sim(cc);
    
        System.out.println(cc.toString()); 
        //f(x,y) ~= g(c,b)

    }
}
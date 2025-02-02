package com;

import java.util.List;

import com.example.Sim;
import com.example.dnf.Conjunction;
import com.example.parser.SimilarityRelationParser;
import com.example.parser.UnificationEquationParser;
import com.example.predicates.SimilarityPredicate;
import com.example.relations.Relation;
import com.example.relations.relationCollection;

public class setUpMain {
    public static void setUpAbstractData(String equation, String relations, String cutValue){
        System.out.println("Equations:" + equation);
        System.out.println("Relations: " + relations);
        System.out.println("Cut Value: " + cutValue);
        SimilarityPredicate sim = UnificationEquationParser.parse(equation, Double.parseDouble(cutValue));
        List<Relation> relList = SimilarityRelationParser.getRelationList(relations);
        Sim.disjunction.add(new Conjunction(sim));
        relationCollection.add(relList);
    }

    public static void main(String[] args) {
        String equation = "k_o(a) ~= b_o(x)";
        String relations = "(m, o, 0.5)";
        String cutValue = "0.5";
        SimilarityPredicate sim = UnificationEquationParser.parse(equation, Double.parseDouble(cutValue));
        List<Relation> relList = SimilarityRelationParser.getRelationList(relations);
        Sim.disjunction.add(new Conjunction(sim));
        relationCollection.add(relList);
        Sim.solve();

    }
    
}

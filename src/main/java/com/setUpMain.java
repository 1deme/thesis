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
    public static void setUpAbstractData(String equation, String cutValue, String relations){
        SimilarityPredicate sim = UnificationEquationParser.parse(equation, Double.parseDouble(cutValue));
        List<Relation> relList = SimilarityRelationParser.getRelationList(relations);
        Sim.disjunction.add(new Conjunction(sim));
        relationCollection.add(relList);
    }
    
}

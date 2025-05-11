package com.example.dnf;
import java.util.LinkedList;
import java.util.List;

import com.example.predicates.SimilarityPredicate;

public class Disjunction {

    public List<Conjunction> conjunctions = new LinkedList<>();

    public Disjunction(List<Conjunction> disjunction){
        this.conjunctions = disjunction;
    }

    public void add(List<SimilarityPredicate> pc){
        conjunctions.add(new Conjunction(pc));
    }

    public void add(Conjunction c){
        conjunctions.add(c);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < conjunctions.size(); i++) {
            sb.append("(");
            sb.append(conjunctions.get(i));
            if (i < conjunctions.size() - 1) {
                sb.append(" \\/ "); 
            }
            sb.append(")");
        }
        return sb.toString();
    }

    
}

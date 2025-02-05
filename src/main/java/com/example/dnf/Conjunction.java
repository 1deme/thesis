package com.example.dnf;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.example.constraintElements.Term;
import com.example.constraintElements.variable;
import com.example.predicates.SimilarityPredicate;

import java.util.LinkedList;


public class Conjunction {

    public List<SimilarityPredicate> constraints = new LinkedList<SimilarityPredicate>();
    public double proximtyDegree = 1;
    public List<SimilarityPredicate> solution = new LinkedList<SimilarityPredicate>();

    public Conjunction(List<SimilarityPredicate> conjunction){
        this.constraints = conjunction;
    }

    public void map(variable from, Term to){
        constraints.stream().map(x -> x.map(from, to)).collect(Collectors.toList());
    }

    public void map(variable from, Term to, Predicate<SimilarityPredicate> cond){
        
        constraints.stream().map(x -> {
            if(cond.test(x)){
                return x.map(from, to);
            }
            else{
                return x;
            }
        } ).collect(Collectors.toList());
    }

    public boolean containt(Term el){
        
        for(SimilarityPredicate pc : constraints){
            if(pc.el1.contains(el) || pc.el2.contains(el)){
                return true;
            }
        }
        return false;
    }

    public Conjunction createCopy(){
        return new Conjunction(constraints.stream().map(x -> x.createCopy()).collect(Collectors.toList()));
    }

    public void add(SimilarityPredicate pc){
        constraints.add(pc);
    }

    public String solutionString(){
        StringBuilder sb = new StringBuilder();
        sb.append(" < ");
        for (int i = 0; i < solution.size(); i++) {
            sb.append(solution.get(i));
            if (i < solution.size() - 1) {
                sb.append(" /\\ "); 
            }
        }
        sb.append("; " + proximtyDegree + " > ");
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < constraints.size(); i++) {
            sb.append(constraints.get(i));
            if (i < constraints.size() - 1) {
                sb.append("/\\"); 
            }
        }
        return sb.toString();
    }

}

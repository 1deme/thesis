package com.example.dnf;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.example.constraintElements.Element;

import com.example.predicates.SimilarityPredicate;

import java.util.LinkedList;


public class Conjunction {

    public List<SimilarityPredicate> constraints = new LinkedList<SimilarityPredicate>();

    public Conjunction(List<SimilarityPredicate> conjunction){
        this.constraints = conjunction;
    }

    public void map(Element from, Element to){
        constraints.stream().map(x -> x.map(from, to)).collect(Collectors.toList());
    }

    public void map(Element from, Element to, Predicate<SimilarityPredicate> cond){
        
        constraints.stream().map(x -> {
            if(cond.test(x)){
                return x.map(from, to);
            }
            else{
                return x;
            }
        } ).collect(Collectors.toList());
    }

    public boolean containt(Element el){
        
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < constraints.size(); i++) {
            sb.append(constraints.get(i));
            if (i < constraints.size() - 1) {
                sb.append(" ∧ "); 
            }
        }
        return sb.toString();
    }

}

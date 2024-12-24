package com.example.dnf;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.example.constraintElements.Element;

import com.example.predicates.PrimitiveConstraint;
import com.example.predicates.SimilarityPredicate;

import java.util.LinkedList;


public class Conjunction {

    public List<PrimitiveConstraint> constraints = new LinkedList<PrimitiveConstraint>();

    public Conjunction(List<PrimitiveConstraint> conjunction){
        this.constraints = conjunction;
    }

    public void map(Element from, Element to){
        constraints.stream().map(x -> x.map(from, to)).collect(Collectors.toList());
    }

    public void map(Element from, Element to, Predicate<PrimitiveConstraint> cond){
        
        constraints.stream().map(x -> {
            if(cond.test(x)){
                return x.map(from, to);
            }
            else{
                return x;
            }
        } ).collect(Collectors.toList());
    }

    public boolean apprSolvedForm(){

        for(int i = 0; i < constraints.size(); i++){
            PrimitiveConstraint curr = constraints.get(i);

            if(curr instanceof SimilarityPredicate && curr.el1.isVariable() && curr.el2.isVariable() ){
                for(int j = 0; j < constraints.size(); j++){
                    PrimitiveConstraint other = constraints.get(j);
                    if(curr instanceof SimilarityPredicate && curr.el1.isVariable() && curr.el2.isVariable() ){
                        continue;
                    }
                    if(j != i && (other.el1.contains(curr.el1) || other.el1.contains(curr.el2))){
                        return false;
                    }
                }
                continue;
            }

            for(int j = i + 1; j < constraints.size(); j++){
                PrimitiveConstraint next = constraints.get(j);
                if(!curr.el1.isVariable()){
                    return false;
                }
                if(curr.el2.contains(curr.el1) || next.el1.contains(curr.el1) || next.el2.contains(curr.el1)){
                    return false;
                }

            }

        }
        return true;
    }

    public boolean containt(Element el){
        
        for(PrimitiveConstraint pc : constraints){
            if(pc.el1.contains(el) || pc.el2.contains(el)){
                return true;
            }
        }
        return false;
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

package com.example.predicates;

import com.example.constraintElements.*;

public class SimilarityPredicate{
    
    public Element el1;
    public Element el2;

    public int RelationId;
    public double CutValue;

    public boolean isSolved = false;

    public SimilarityPredicate map(Element from, Element to){
        el1 = el1.map(from, to);
        el2 = el2.map(from, to);
        return this;
    }

    public SimilarityPredicate(Element el1, Element el2, int RelationId, double CutValue){
        this.el1 = el1;
        this.el2 = el2;
        this.RelationId = RelationId;
        this.CutValue = CutValue;
    }

    public SimilarityPredicate setRelationId(int relationId){
        this.RelationId = relationId;
        return this;
    }

    public SimilarityPredicate setCutValue(float CutValue){
        this.CutValue = CutValue;
        return this;
    }

    public SimilarityPredicate createCopy(){
        return new SimilarityPredicate(el1.createCopy(), el2.createCopy(), RelationId, CutValue);
    }

    @Override
    public String toString(){
        return el1.toString() + " ~=" + (isSolved ? " " : "? ") + Integer.toString(RelationId) + ", " + Double.toString(CutValue) + " " + el2.toString();
    }
    
}

package com.example.predicates;

import com.example.constraintElements.*;

public class SimilarityPredicate extends PrimitiveConstraint{

    public int RelationId;
    public double CutValue;

    public SimilarityPredicate(Element el1, Element el2, int RelationId, double CutValue){
        super(el1, el2);
        this.RelationId = RelationId;
        this.CutValue = CutValue;
    }

    public PrimitiveConstraint setRelationId(int relationId){
        this.RelationId = relationId;
        return this;
    }

    public PrimitiveConstraint setCutValue(float CutValue){
        this.CutValue = CutValue;
        return this;
    }

    @Override
    public SimilarityPredicate createCopy(){
        return new SimilarityPredicate(el1.createCopy(), el2.createCopy(), RelationId, CutValue);
    }

    @Override
    public String toString(){
        return el1.toString() + " ~=" + (isSolved ? " " : "? ") + Integer.toString(RelationId) + ", " + Double.toString(CutValue) + " " + el2.toString();
    }

    
    
}

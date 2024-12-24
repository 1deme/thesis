package com.example.predicates;

import com.example.constraintElements.*;

public class EqualityPredicate extends PrimitiveConstraint{

    public EqualityPredicate(Element el1, Element el2){
        super(el1, el2);
    }

    @Override
    public EqualityPredicate createCopy(){
        return new EqualityPredicate(el1.createCopy(), el2.createCopy());
    }
    
}

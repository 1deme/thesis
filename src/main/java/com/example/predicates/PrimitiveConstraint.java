package com.example.predicates;
import com.example.constraintElements.Element;

public class PrimitiveConstraint {

    public Element el1;
    public Element el2;

    public boolean isSolved = false;

    public PrimitiveConstraint(Element el1, Element el2){
        this.el1 = el1;
        this.el2 = el2;
    }


    public String toString(){
        return el1.toString() + " =" + (isSolved ? "" : "?") + el2.toString();
    }

    public PrimitiveConstraint createCopy(){
        return null;
    }

    public PrimitiveConstraint map(Element from, Element to){
        el1 = el1.map(from, to);
        el2 = el2.map(from, to);
        return this;
    }

}

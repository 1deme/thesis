package com.example.relations;

import com.example.constraintElements.Term;

public class Relation {

    public Term el1;
    public Term el2;
    public double value;

    public Relation(Term el1, Term el2, double value) {
        this.el1 = el1;
        this.el2 = el2;
        this.value = value;
    }
}

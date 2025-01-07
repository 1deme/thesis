package com.example.relations;

import com.example.constraintElements.Term;
import com.example.constraintElements.FunctionSymbol;

public class Relation<T> {

    public T el1;
    public T el2;
    public int relId;
    public double value;

    public Relation(T el1, T el2, int relId, double value) {
        this.el1 = el1;
        this.el2 = el2;
        this.relId = relId;
        this.value = value;
    }
}

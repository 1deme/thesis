package com.example.relations;

import com.example.constraintElements.FunctionSymbol;

public class Relation {

    public FunctionSymbol el1;
    public FunctionSymbol el2;
    public double value;

    public Relation(FunctionSymbol el1, FunctionSymbol el2, double value) {
        this.el1 = el1;
        this.el2 = el2;
        this.value = value;
    }
}

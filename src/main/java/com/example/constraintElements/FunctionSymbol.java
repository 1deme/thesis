package com.example.constraintElements;

public class FunctionSymbol{

    public char name;
    public boolean isOrdered;

    public FunctionSymbol (char name, boolean ordered){
        this.name = name;
        this.isOrdered = ordered;
    }

    public String toString(){
        return Character.toString(name) + (isOrdered ? "_o" : "_u");
    }

    public FunctionSymbol createCopy(){
        return new FunctionSymbol(name, isOrdered);
    }

    public FunctionSymbol clone(){
        return new FunctionSymbol(name, isOrdered);
    }

    public boolean equals(FunctionSymbol other){
        return this.name == other.name && this.isOrdered == other.isOrdered;
    }

}

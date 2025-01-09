package com.example.constraintElements;

public class FunctionSymbol{

    char name;
    boolean isOrdered;

    public FunctionSymbol (char name, boolean ordered){
        this.name = name;
        this.isOrdered = ordered;
    }

    public String toString(){
        return Character.toString(name);
    }

    public FunctionSymbol createCopy(){
        return new FunctionSymbol(name, isOrdered);
    }

    public FunctionSymbol clone(){
        return new FunctionSymbol(name, isOrdered);
    }

}

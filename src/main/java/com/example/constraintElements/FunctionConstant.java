package com.example.constraintElements;

public class FunctionConstant extends FunctionSymbol {

    public FunctionConstant(char name) {
        super(name);
    }

    @Override
    public boolean contains(Element el) {
        return el == this;
    }

}
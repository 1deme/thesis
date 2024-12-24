package com.example.constraintElements;

public class FunctionSymbol implements Element{
    char name;

    public FunctionSymbol (char name){
        this.name = name;
    }

    public String toString(){
        return Character.toString(name);
    }

    @Override
    public Element map(Element from, Element to) {
        if(this.equals(from)){
            return to;
        }
        return this;
    }

    @Override
    public boolean contains(Element el) {
        return el == this;
    }

    @Override
    public char getName() {
        return name;
    }

    @Override
    public FunctionSymbol createCopy(){
        return new FunctionSymbol(name);
    }


}

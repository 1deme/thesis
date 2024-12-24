package com.example.constraintElements;

public class TermVariable implements Term {
    char name;

    public TermVariable(char name){
        this.name = name;
    }

    @Override
    public String toString(){
        return Character.toString(name);
    }

    @Override
    public Element map(Element from, Element to){
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
    public Element createCopy() {
        return new TermVariable(name);
    }

    @Override
    public boolean isVariable(){return true;}
   
}

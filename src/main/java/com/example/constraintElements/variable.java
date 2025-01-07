package com.example.constraintElements;

public class variable implements Term {
    char name;

    public variable(char name){
        this.name = name;
    }

    @Override
    public String toString(){
        return Character.toString(name);
    }

    @Override
    public Term map(variable from, Term to){
        if(this.equals(from)){
            return to;
        }
        return this;
    }

    @Override
    public boolean contains(Term el) {
        return el == this;
    }

    @Override
    public char getName() {
        return name;
    }

    @Override
    public Term createCopy() {
        return new variable(name);
    }

    @Override
    public boolean isVariable(){return true;}
   
}

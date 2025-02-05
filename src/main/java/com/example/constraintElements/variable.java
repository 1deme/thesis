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
        if(el.isVariable()){
            return el.getName() == this.name;
        }
        return false;
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

    @Override
    public boolean equals(Object obj){
        if(obj instanceof variable){
            return ((variable) obj).name == this.name;
        }
        return false;
    }
   
}

package com.example.constraintElements;

import java.util.Arrays;

public class FunctionApplication implements Term{

    public FunctionSymbol functionSymbol;
    public Term[] args;

    public FunctionApplication(char name, boolean isOrdered, Term[] args){
        this.functionSymbol = new FunctionSymbol(name, isOrdered);
        this.args = args;
    }

    public FunctionApplication(FunctionSymbol functionSymbol, Term[] args){
        this.functionSymbol = functionSymbol;
        this.args = args;
    }

    @Override
    public Term map(variable from, Term to) {
        FunctionSymbol newFunctionSymbol = functionSymbol.createCopy();
        Term[] newArgs = new Term[args.length];
        for(int i = 0; i < args.length; i++){
            newArgs[i] = args[i].map(from, to);            
        }
        return new FunctionApplication(newFunctionSymbol, newArgs);
    }

    @Override
    public String toString(){
        if(args.length == 0){
            return functionSymbol.toString();
        }
        return functionSymbol + "(" + String.join(", ", Arrays.stream(args).map(Term::toString).toArray(String[]::new)) + ")"; 
    }

    @Override
    public boolean contains(Term el) {
        for(int i = 0; i < args.length; i++){
            if(args[i].contains(el)){
                return true;
            }
        }
        return false;
    }

    @Override
    public char getName() {
        throw new UnsupportedOperationException("Unimplemented method 'contains'");
    }

    @Override
    public FunctionApplication createCopy() {
        Term[] copy = new Term[args.length];
        for(int i = 0; i < args.length; i++){
            copy[i] = (Term) args[i].createCopy();
        }
        return new FunctionApplication(functionSymbol.createCopy(), copy);
    }

    @Override
    public int arity() {
        return args.length;
    }

    @Override
    public boolean isOrdered(){
        return functionSymbol.isOrdered;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof FunctionApplication){
            FunctionApplication other = (FunctionApplication) obj;
            if(!functionSymbol.equals(other.functionSymbol)){
                return false;
            }
            if(args.length != other.args.length){
                return false;
            }
            for(int i = 0; i < args.length; i++){
                if(!args[i].equals(other.args[i])){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
}

package com.example.constraintElements;

import java.util.Arrays;

public class FunctionApplication implements Term{

    public FunctionSymbol functionSymbol;
    public Term[] args;
    private boolean isOrdered;

    public FunctionApplication(FunctionSymbol functionSymbol, Term[] args, boolean isOrdered){
        this.functionSymbol = functionSymbol;
        this.args = args;
        this.isOrdered = isOrdered;
    }

    @Override
    public Element map(Element from, Element to) {
        FunctionSymbol newFunctionSymbol = (FunctionSymbol) functionSymbol.map(from, to);
        Term[] newArgs = new Term[args.length];
        for(int i = 0; i < args.length; i++){
            newArgs[i] = (Term) args[i].map(from, to);            
        }
        return new FunctionApplication(newFunctionSymbol, newArgs, isOrdered);
    }

    @Override
    public String toString(){
        return functionSymbol + "(" + String.join(", ", Arrays.stream(args).map(Term::toString).toArray(String[]::new)) + ")"; 
    }

    @Override
    public boolean contains(Element el) {
        if(functionSymbol.contains(el)){
            return true;
        }
        for(int i = 0; i < args.length; i++){
            if(args[i].contains(el)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isAtomic(){return false;}

    
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
        return new FunctionApplication(functionSymbol.createCopy(), copy, isOrdered);
    }

    public boolean isOrdered(){
        return isOrdered;
    }
    
}

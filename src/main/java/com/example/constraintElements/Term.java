package com.example.constraintElements;

public interface Term{
    Term map(variable from, Term to);
    public String toString();
    public boolean contains(Term el);
    public char getName();
    public Term createCopy();
    default boolean isOrdered(){throw new UnsupportedOperationException("Unimplemented method 'isOrdered'");}
    default int arity(){throw new UnsupportedOperationException("Unimplemented method 'arity'");}
    default boolean isVariable(){return false;}
}

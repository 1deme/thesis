package com.example.constraintElements;

public interface Element {
    Element map(Element from, Element to);
    public String toString();
    public boolean contains(Element el);
    public char getName();
    public Element createCopy();
    default boolean isVariable(){return false;}
    default boolean isAtomic(){return true;}
}

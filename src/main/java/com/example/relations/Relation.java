package com.example.relations;
import com.example.constraintElements.Element;

public class Relation {

    public Element el1;
    public Element el2;
    public int relId;
    public double value;

    public Relation(Element el1, Element el2, int relId, double value) {
        this.el1 = el1;
        this.el2 = el2;
        this.relId = relId;
        this.value = value;
    }

}

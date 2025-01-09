package com.example.relations;

import java.util.LinkedList;
import java.util.List;
import com.example.constraintElements.Term;

public class relationCollection {

    public static List<Relation> collection = new LinkedList<>();

    public static void add(Term el1, Term el2, Double value) {
        collection.add(new Relation(el1, el2, value));
    }

    public static  Double lookup(Term el1, Term el2) {
        if (el1.toString().equals(el2.toString())) {
            return 1.0;
        }

        for (Relation rel : collection) {
            if (  ((rel.el1.toString().equals(el1.toString()) && rel.el2.toString().equals(el2.toString())) ||
                    (rel.el1.toString().equals(el2.toString()) && rel.el2.toString().equals(el1.toString())))) {
                return rel.value;
            }
        }

        return .0;
    }
}

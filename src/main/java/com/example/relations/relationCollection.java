package com.example.relations;

import java.util.LinkedList;
import java.util.List;

import com.example.constraintElements.FunctionSymbol;

public class relationCollection {

    public static List<Relation> collection = new LinkedList<>();

    public void add(FunctionSymbol el1, FunctionSymbol el2, Double value) {
        collection.add(new Relation(el1, el2, value));
    }

    public static  Double lookup(FunctionSymbol el1, FunctionSymbol el2) {
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

    public static List<FunctionSymbol> getneighborhood(FunctionSymbol el, Double threshold) {
        List<FunctionSymbol> neighborhood = new LinkedList<>();
        neighborhood.add(el);
        for (Relation rel : collection) {
            if (rel.el1.toString().equals(el.toString()) && rel.value >= threshold) {
                neighborhood.add(rel.el2);
            }
            if (rel.el2.toString().equals(el.toString()) && rel.value >= threshold) {
                neighborhood.add(rel.el1);
            }
        }
        return neighborhood;
    }

    public static boolean checkTransitivity() {
        // Get all unique FunctionSymbols in the collection
        List<FunctionSymbol> elements = new LinkedList<>();
        for (Relation relation : collection) {
            if (!elements.contains(relation.el1)) elements.add(relation.el1);
            if (!elements.contains(relation.el2)) elements.add(relation.el2);
        }

        System.out.println(elements);

        // Check transitivity for all x, y, z combinations
        for (FunctionSymbol x : elements) {
            for (FunctionSymbol y : elements) {
                for (FunctionSymbol z : elements) {
                    double Rxy = lookup(x, y);
                    double Rxz = lookup(x, z);
                    double Rzy = lookup(z, y);
                    
                    // Check if fuzzy transitivity condition is violated
                    if (Rxy < Math.min(Rxz, Rzy)) {
                        return false; // Transitivity is violated
                    }
                }
            }
        }

        return true; // All checks passed, the relation is transitive
    }
}

package com.example.relations;
import com.example.constraintElements.Element;

import java.util.LinkedList;
import java.util.List;

public class relationCollection {

    public static List<Relation> collection = new LinkedList<>();


    public static void add(Element el1, Element el2, int relId, Double value) {
        collection.add(new Relation(el1, el2, relId, value));
    }

    public static Double lookup(Element el1, Element el2, int relId) {
        if(el1.toString().equals(el2.toString())){
            return 1.0;
        }

        for(Relation rel : collection){
            if(
                rel.relId == relId 
                && ((rel.el1.toString().equals(el1.toString()) && rel.el2.toString().equals(el2.toString())) ||
                 (rel.el1.toString().equals(el2.toString()) && rel.el2.toString().equals(el1.toString())))
            ){
                return rel.value;
            }
        }
        
        return .0;
    }
    

}

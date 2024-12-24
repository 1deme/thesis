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

        for(Relation rel : collection){
            if(
                rel.relId == relId 
                && ((rel.el1 == el1 && rel.el2 == el2) || (rel.el1 == el2 && rel.el2 == el1))
            ){
                return rel.value;
            }
        }
        
        return 2.0;
    }
    

}

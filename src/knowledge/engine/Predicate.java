/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.knowledge.engine;

import java.util.ArrayList;

/**
 *  an object which represents a (literal) Predicate within a Triple object. Each Predicate has an
 * identifier, which is a String that represents the Predicate which is a property of the Subject which
 * connects the Subject to the Object in the Triple
 * 
 * @author willpassidomo
 */
public class Predicate {
    
    /**
     * the cache for the existing, instatiated Predicate Objects per the Flyweight Pattern
     */
    
    private static ArrayList<Predicate> predicates = new ArrayList<>();
    
     /**
      * the identifier is the String representation of the Predicate which is a property of the Subject which
      * connects the Subject to the Object in the Triple
      */
    
    private final String identifier;
    Predicate testPred;
    
    /**
     *  instantiates a Node object with the passed identifier
     * @param identifier the Identifier is the String representation of the Predicate which is a property of the Subject which
     * connects the Subject to the Object in the Triple
     */
    
    private Predicate(String identifier){
        this.identifier = identifier;
    }
    
    /**
     *
     * This method follows the flyweight pattern which keeps a cache of existing Predicate objects 
     * which is checked for possible matches for given identifier when method is invoked. If
     * Predicate with the given identifier already exists in the cache, said Predicate is returned, otherwise new instance is created, stored in cache and returned
     * 
     * 
     * @param identifier the Identifier is the String representation of the Predicate which is a property of the Subject which
     * connects the Subject to the Object in the Triple
     * @return  the corresponding Predicate object with the inputed identifier, either previously initilized from the Predicate cache or newly created in accordance with the Flyweight Pattern
     */
    protected static Predicate newPredicate(String identifier) {
        for (Predicate predicate: predicates) {
            if (predicate.equals(identifier)){
                    return predicate;
            }
        }
        Predicate newPredicate = new Predicate(identifier);
        predicates.add(newPredicate);
        return newPredicate;
    }
    
    /**
     *  returns the Identifier associated with the Predicate Object.
     * the Identifier is the String representation of the Predicate which is a property of the Subject which
     * connects the Subject to the Object in the Triple
     * 
     * @return the identifier String for the particular Predicate
     */
    
    public String getIdentifier() {
        return this.identifier;
    }
    
    /**
     * equals was @Override for the purpose of comparing Predicate instances. the Overridden method first
     * checks that the parameter Object is an instance of Predicate, then casts it as Predicate. If true, the newly
     * casted Predicate's identifier is compared to the particular Predicate's Identifier using .equalsIgnoreCase(String)
     * and results are returned, otherwise returns false.
     * 
     * 
     * @param obj the Object the given Predicate's equality will be tested against
     * @return true if equal, false if not equal 
     */
    
    @Override
    public boolean equals (Object obj) {
        if (obj instanceof Predicate){
            testPred = (Predicate)  obj;
            if (this.getIdentifier().equalsIgnoreCase(testPred.getIdentifier())) {
            return true;
            }
        }
        return false;
    
    }
    
}

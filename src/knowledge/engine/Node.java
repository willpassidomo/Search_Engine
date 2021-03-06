/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.knowledge.engine;

import java.util.ArrayList;

/**
 *  an Object which represents a Subject or Object within a Triple object. Each Node has an
 * identifier, which is a String that represents the Subject or Object i.e, the Proper Noun which is linked
 * to the opposite (Subject or Object) by a Predicate to form a Triple
 * @author willpassidomo
 */
public class Node {
    
    /**
     * a String that represents the Subject or Object i.e, the Proper Noun which is linked
     * to the opposite (Subject or Object) by a Predicate to form a Triple
     */
    
    private final String identifier;
    
    /**
     * the cache for the existing, instatiated Predicate Objects per the Flyweight Pattern
     */
    
    private static ArrayList<Node> nodes = new ArrayList<>();
    
    /**
      * the identifier is the String representation of the Predicate which is a property of the Subject which
      * connects the Subject to the Object in the Triple
      */
    
    private Node testNode;
    
    /**
     *   instantiates a Node object with the passed identifier
     * 
     * @param identifier The String which represents the identity of the Node, i.e the Object or 
     *                   Subject (Noun) the Node is to represent
     */
    
    private Node(String identifier) {
        this.identifier = identifier;
    }
    
    /**
     *  for the creation or retreival of a Node object for a given identifier.
     * 
     * This method follows the flyweight pattern which keeps a cache of existing Node objects 
     * which is checked for possible matches for given identifier when method is invoked. If
     * Node with the given identifier already exists in the cache, said Node is returned, otherwise new instance is created, stored in cache and returned
     * 
     * @param identifier    The String which represents the identity of the Node, i.e the Object or 
     *                      Subject (Noun) the Node is to represent
     * @return  the corresponding Node object with the inputed identifier, either previously initilized from the Node cache or newly created in accordance with the Flyweight Pattern
     */
    protected static Node newNode(String identifier) {
        for (Node node: nodes) {
            if (node.equals(identifier)) {
                return node;
            }
        }
        Node newNode = new Node(identifier);
        nodes.add(newNode);
        return newNode;
        
    }
    
    /**
     *  returns the Identifier associated with the Node Object.
     * the Identifier is the String representation of the Node i.e the Object or 
     * Subject (Noun) the Node is to represent
     * 
     * 
     * @return  the identifier String for the particular Node
     */
    
    public String getIdentifier() {
        return this.identifier;            
    }
    
    /**
     * equals was @Override for the purpose of comparing Node instances. the overridden method first
     * checks that the parameter Object is an instance of Node, then casts it as node. If true, the newly
     * casted Node's identifier is compared to the particular Node's Identifier using .equalsIgnoreCase(String)
     * and results are returned, otherwise returns false.
     * 
     * @param obj the Object the given Node's equality will be tested against
     * @return true if equal, false if not equal 
     */
    
    @Override
    public boolean equals(Object obj){
        if (obj instanceof Node) {
            testNode = (Node) obj;
            if (this.getIdentifier().equalsIgnoreCase(testNode.getIdentifier())){
            return true;
            }
        }
        return false;
    }
}

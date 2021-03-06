/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.knowledge.engine;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *  the program has a single instance of KnowldgeGraph which serves as the knowledge or search engine.
 * the instance of KnowldegeGrph can be obtained from the getInstance() method.
 * @author willpassidomo
 */
public class KnowledgeGraph {
    private static Map<String,Node> nodeMap = new TreeMap<>();
    private static Map<String,Predicate> predicateMap = new TreeMap<>();
    private static Map<String,Triple> tripleMap = new TreeMap<>();
    private static Map<String, Set<Triple>> queryMapSet = new TreeMap<>();
    private final Node testNode = Node.newNode("?");
    private final Predicate testPredicate = Predicate.newPredicate("?");

    /**
     *  returns the singleton instance of KnowledgeGraph. Other than this one, the 
     * methods within the KnowledgeGraph class are instance methods, which require this
     * object in order to be invoked
     * @return the singleton KnowledgeGraph object
     */
    public static KnowledgeGraph getInstance() {
        return KnowledgeGraphHolder.INSTANCE;
    }
    
    /**
     *  provides a means to pass a Set of Triple Objects to the KnowledgeGraph instance. Imported Triples are added to the tripleMap and the
     * of which, member Triple's Subjects (Node Objects), Predicates (Predicate Objects) and Objects (Node Objects)
     * are added to the nodeMap and predicateMap and used to update the queryMapSet in order
     * to maintain optimization of search results. when method is called, updateQueryMapSet() is automatically called
     * in order to insure newly imported Triple objects are part of search results.
     * @param triples a Set of Triple Objects
     */
    public void importTriples (Set<Triple> triples) {
        for (Triple triple: triples) {
            tripleMap.put(triple.getIdentifier(), triple);
        }
        updateQueryMapSet();
    }
    
    protected void importTriples(Triple triple) {
        tripleMap.put(triple.getIdentifier(), triple);
        updateQueryMapSet();
    }
      /*  System.out.println("TRIPLE MAP");
                    for (Triple tripleValue: tripleMap.values()) {
                        System.out.println(tripleValue.getIdentifier());
                    }   */
        
    
    
    /**
     *  a search method which takes a parameter structured "Subject Predicate Object" and returns a Set of Triple objects which match the specified query String in O(1) time, as 
     * possible query results are preIndexed. query String is case insensitive, but space sensitive (there must be exactly one space between
     * Subject and Predicate and Predicate and Object in the query String, and no leading spaces). The special character "?" is reserved and represents
     * a wildcard placeholder. This means that any Subject, Predicate or Object space or combination of the three left as a "?" in the query will not 
     * filter results based on the respective field left as such. If all three parts of the query are represented by a "?", a Set with all Triple Objects
     * will be returned.
     * @param query a String structured as "Subject Predicate Object" which allows for the wildcard "?" character
     * @return a Set of matching Triple objects
     */
    public Set<Triple> excecuteQuery (String query) {
        return queryMapSet.get(query.toLowerCase());
    }
    
    /**
     * pre-forms query results and logs them in queryMapSet by calculating 8 possible variations of queries
     * which would result in returning the particular Triple which is being iterated over and adds the Triple to the 
     * Set. for example;
     * Triple: A B C
     * 
     * would be mapped under the following keys:
     * A B C,
     * A B ?,
     * A ? C,
     * ? B C,
     * ? ? C,
     * A ? ?,
     * ? B ?,
     * ? ? ?
     */
    
    private void updateQueryMapSet() {
        Triple testTriple;
        for (Triple triple: tripleMap.values()) {
            QueryConditionalUpdate(triple, triple.getIdentifier().toLowerCase());
            QueryConditionalUpdate(triple, Triple.tripleToString(triple.getSubject(), triple.getPredicate(), testNode).toLowerCase());
            QueryConditionalUpdate(triple, Triple.tripleToString(triple.getSubject(), testPredicate, triple.getObject()).toLowerCase());
            QueryConditionalUpdate(triple, Triple.tripleToString(testNode, triple.getPredicate(), triple.getObject()).toLowerCase());
            QueryConditionalUpdate(triple, Triple.tripleToString(testNode, testPredicate, triple.getObject()).toLowerCase());
            QueryConditionalUpdate(triple, Triple.tripleToString(triple.getSubject(), testPredicate, testNode).toLowerCase());
            QueryConditionalUpdate(triple, Triple.tripleToString(testNode, triple.getPredicate(), testNode).toLowerCase());
            QueryConditionalUpdate(triple, Triple.tripleToString(testNode, testPredicate, testNode).toLowerCase());
        }
    }
           
    private void QueryConditionalUpdate (Triple triple, String Identifier) { 
        //System.out.println(Identifier);
            if (!queryMapSet.containsKey(Identifier)) {
                queryMapSet.put(Identifier, new TreeSet());        
            }
                queryMapSet.get(Identifier).add(triple);
    }       

    /**
     *  returns any Node object, Object or Predicate, which matches the passed identifier. returns null if there is no match
     * 
     * @param identifier a String which represents the Node's identifier
     * @return a  Node Object with an matching identifier to the passed parameter, or null if one does not exist 
     */
    public Node getNode(String identifier) {
        return nodeMap.get(identifier);
    }
    
    /**
     *  returns any Predicate object, which matches the passed identifier. returns null if there is no match
     * 
     * @param identifier a String which represents the Node's identifier
     * @return a Predicate Object with a matching identifier to the passed parameter, or null if one does not exist
     */
    public Predicate getPredicate(String identifier) {
        return predicateMap.get(identifier);
    }
    
    /**
     *  returns a Triple object, which matches the passed subject (Node Object), predicate,(Predicate Object), and object (Node Object) or null
     * if there is no match
     * 
     * @param subject a Node object representing the Triple's Subject
     * @param predicate a Predicate object representing the Triple's Predicate
     * @param object a Node object representing the Triple's Object
     * @return a Triple Object with a matching Subject, Predicate and Object to the passed parameters, or null if one does not exist
     */
    public Triple getTriple(Node subject, Predicate predicate, Node object) {
        String tripleIdentifier = Triple.tripleToString(subject, predicate, object);
        if (tripleMap.containsKey(tripleIdentifier)) {
                return tripleMap.get(tripleIdentifier);
            }else{
                try {
                    Triple newTriple = new Triple(subject, predicate, object);
                    tripleMap.put(newTriple.getIdentifier(), newTriple);
                    return newTriple;
                }
                catch (ImportException e) {
                    return null;
                }
            }
    }
    
    /**
     * a private class which has no methods and acts solely as a holder for the singleton class KnowledgeGraph's sole instance 
     * in accordance with Singleton pattern best practices (according to Netbeans IDE 8.0)
     */
    
    private static class KnowledgeGraphHolder {

        private static final KnowledgeGraph INSTANCE = new KnowledgeGraph();
    }
}

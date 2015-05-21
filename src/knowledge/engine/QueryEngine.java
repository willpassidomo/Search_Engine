/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.knowledge.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;

/**
 * the QueryEngine is used to query the Triples stored in the Knowledge Graph. multiple
 * queries in a file formate are supported by the public method, executeQuueryFile(String pathName), and
 * individual, manual queries are supported by public executeQuery(String triple) method. the QueryEngine
 * allows for the reserved character "?" as a wildcard to be used which signifies that the field within the Triple
 * which contains the "?" will not be filtered
 * @author willpassidomo
 */

public class QueryEngine {
    private static int count = 0;
    private static String[] querySplit;
    
    /**
     *  executes one or multiple queries serially derived from a document associated with the "pathName" passed  
     *  as a parameter. Individual queries are parsed from the selected document
     * and executed via the executeQuery(String triple) method contained within this class.
     * 
     * @param pathName A pathname string  
     */
    public static void executeQueryFile(String pathName) {
        try {
            File file = new File(pathName); 
            Scanner scanner = new Scanner(file);            
            scanner.useDelimiter("\\.");
            while (scanner.hasNext()) { 
                try {
                executeQueryFromDoc(scanner.next());
                }
                catch (QueryEngineException e){
                }
                }
            }
        catch (FileNotFoundException e) {
           System.out.println("the file does not exist");
           System.out.println("Please enter a new file pame:");
           Scanner scanner = new Scanner(System.in);
           executeQueryFile(scanner.nextLine());
            }
        }
        
    /**
     *  executes a single query based on the parameter triple, which is structured "Subject Predicate Object" in O(1) time, as 
     * possible query results are preIndexed. query String is case insensitive, but space sensitive (there must be exactly one space between
     * Subject and Predicate and Predicate and Object in the query String, and no leading spaces). The special character "?" is reserved and represents
     * a wildcard placeholder. This means that any Subject, Predicate or Object space or combination of the three left as a "?" in the query will not 
     * filter results based on the respective field left as such. If all three parts of the query are represented by a "?", a Set with all Triple Objects
     * will be returned.
     * @param triple a String structured as "Subject Predicate Object", which allows for the wildcard "?" character
     * 
     */        
        public static void executeQuery(String triple) {
            try {
                executeQueryFromDoc(triple);
            }
            catch (QueryEngineException e) {
                System.out.print("Malformed Query: ("+triple+") -");
                System.out.println(e.getDescription());
                Scanner scanner = new Scanner(System.in);
                System.out.println("please enter new query:");
                executeQuery(scanner.nextLine());
            }
        }
        
        /**
         * this method was added to the original design in order to differentiate how
         * errors are handled depending on whether they are being generated from a Query File
         * or if they are being inputed manually. this method is used for queries from a document with
         * ,likely, multiple queries. If an error is encountered, they file will continue to read queries
         * but output that an error occured for the malformed query. The public method, executeQuery(String triple)
         * is used for manually inputed queries through the public interface. In the executeQuery(String triple)
         * method, if a malformed query is recognized, it will prompt the user to reenter a query and display no
         * results
         * @param triple a String structured as "Subject Predicate Object", which allows for the wildcard "?" character
         * @throws QueryEngineException throws exception when triple parameter is malformed for a variety of reasons, including- more than 3 space delineated strings in the parameter "triple", less than 3 space-delineated strings in the parameter "triple",
         * a space-delineated String consisting solely of the reserved character "?" in the parameter "triple"
         */
        
        private static void executeQueryFromDoc(String triple) throws QueryEngineException {
        count++;
        querySplit = triple.split(" ");
        if (querySplit.length == 6) {
            throw new QueryEngineException("Improper Syntax: missing period", count,triple);
        }
        if (querySplit.length == 1) {
            querySplit[0] = querySplit[0].trim();
            if (querySplit[0].length() == 0){
                return;
            }
        }
        if ((querySplit.length > 3 || querySplit.length < 3)&& querySplit.length > 0){
            throw new QueryEngineException("Improper Syntax: Triple contains too many/too few Items", count, triple);
        }
        
        if (querySplit.length == 3){
        for (int i = 0; i< querySplit.length; i++){
            querySplit[i] = querySplit[i].trim();
        }
        String properTriple = querySplit[0]+ " "+ querySplit[1]+" "+ querySplit[2];
        Set<Triple> results = KnowledgeGraph.getInstance().excecuteQuery(properTriple);
        System.out.println("Query ("+querySplit[0]+" "+querySplit[1]+" "+querySplit[2]+ ") - complete");
        System.out.println("\tResults: ");
        try {
        for (Triple result: results) {
            System.out.println("\t\t"+result);
            }
        System.out.println();
        }
        catch (NullPointerException e) {
            System.out.println("\t\t<null>");
        }
        }
    }
    }
    


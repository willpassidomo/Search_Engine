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
 *  a class which can be used to accumulate Triples for the Knowledge Engine. Collects Triples by parsing 
 * documents using the public importTripleFile(String filePath) or by manually inputing a Triple in the 
 * form of a String in the public checkTriple(String triple method).
 * @author willpassidomo
 */
public class Importer {
       
    private static int count = 0;
    private static String[] tripleSplit;

    /**
     *
     * @param args
     */

    
    /**
     *  imports one or multiple triples, serially, derived from a document associated with the "pathName" passed 
     *  as a parameter. Individual triples are parsed from the selected document
     * and executed via the checkTriples(String triple) method contained within this class. executing this method will
     * check legality of all Triples, create all associated objects (Node, Predicate and Triple) and preIndex the results to
     * optimize for search queries
     * 
     * @param pathName A pathname string 
     */
    public static void importTripleFile(String pathName) {
        try {
            File file = new File(pathName); 
            Scanner scanner = new Scanner(file);            
            scanner.useDelimiter("\\.");
            while (scanner.hasNext()) { 
                try {
                checkTriple(scanner.next());
                }
                catch (ImportException e){
                }
                }
            KnowledgeGraph.getInstance().importTriples(Triple.getTriples());
            }
        catch (FileNotFoundException e) {
           System.out.println("the file does not exist");
           System.out.println("Please enter a new file name:");
           Scanner scanner = new Scanner(System.in);
           importTripleFile(scanner.nextLine());
            }
        }
    
    /**
     * checks syntax of individual "possible triples" passed from the period-delineated Strings from the
     * importTripleFile(string pathName) method. if syntax is correct (Subject, Predicate, Object) with none consisting
     * solely of the reserved character, "?", then the 3 space-delineated Strings are passed to instantiate a new Triple 
     * object in the Triple class and cached automatically
     * @param triple a String structured as "Subject Predicate Object"
     * @throws ImportException throws exception when triple parameter is malformed for a variety of reasons, including- more than 3 space delineated strings in the parameter "triple", less than 3 space-delineated strings in the parameter "triple",
     * a space-delineated String consisting solely of the reserved character "?" in the parameter "triple"
     */
    
    public static void checkTriple(String triple) throws ImportException {
        count++;
        tripleSplit = triple.split(" ");
        if (tripleSplit.length > 4 && (tripleSplit.length -1) % 2 == 0) {
            throw new ImportException("Improper Syntax: missing period", count, triple);
        }
        if (tripleSplit.length == 1) {
            tripleSplit[0] = tripleSplit[0].trim();
            if (tripleSplit[0].length() == 0){
                return;
            }
        }
        if ((tripleSplit.length > 3 || tripleSplit.length < 3)&& tripleSplit.length > 0){
            throw new ImportException("Improper Syntax: Triple contains too many/too few Items", count, triple);
        }
        
        if (tripleSplit.length == 3){
        for (int i = 0; i< tripleSplit.length; i++){
            tripleSplit[i] = tripleSplit[i].trim();
        }
        
        if (tripleSplit[0].equals("?") || tripleSplit[1].equals("?") || tripleSplit[2].equals("?")){
            throw new ImportException("Illegal use of reserved character: Subject/Predicate/Object: '?' is not a valid object",count, triple);
        }
        
        Triple.newTriple(tripleSplit[0],tripleSplit[1],tripleSplit[2],count);
        }
        System.out.println("   Triple #"+count+ " - complete");
    }
}

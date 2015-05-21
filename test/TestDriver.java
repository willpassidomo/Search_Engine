/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cscie97.asn1.test;

/**
 *
 * @author willpassidomo
 */
import cscie97.asn1.knowledge.engine.Importer;
import static cscie97.asn1.knowledge.engine.Importer.importTripleFile;
import cscie97.asn1.knowledge.engine.KnowledgeGraph;
import cscie97.asn1.knowledge.engine.QueryEngine;
import java.util.InputMismatchException;
import java.util.Scanner;
import cscie97.asn1.knowledge.engine.Triple;
public class TestDriver {
        private static Scanner scanner;
        public static void main (String[] args) {
            scanner = new Scanner(System.in);
            option();
        }
        
        private static void option() {
            scanner = new Scanner(System.in);
            System.out.println("Enter\n(1) to run TestDriver Program again\n(2)for Full Program\n(3) to exit");
            try{ switch(scanner.nextInt()) {
                case 1:
                    testDriverProgram();
                    return;
                case 2:
                    fullProgram();
                    return;
                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Improper entry!!!");
                    option();
            }
            }
            catch (InputMismatchException e) {
                System.out.println("Improper entry!!!");
                option();
            }
        }
        
        private static void testDriverProgram() {
            System.out.println("Import Triple File- please enter a file path:");
            scanner = new Scanner(System.in);
            importTripleFile(scanner.nextLine());
            System.out.println("Enter a query file path");
            QueryEngine.executeQueryFile(scanner.nextLine());
            System.out.println("Complete!");
            option();
        }
        
        private static void fullProgram() {
            System.out.println("Full Program:\nEnter\n(1) to import Triple File\n(2) to manually input a Triple\n(3) to execute a Query File \n(4) to manually execute a Query \n(5) to list all Subjects and Objects \n(6) to list all Triples\n(7) to view queryMap\n(8) return to main menu");
            scanner = new Scanner(System.in);
            switch(scanner.nextInt()){
                case 1:
                    System.out.println("Import Triple File- please enter a file path:");
                    scanner = new Scanner(System.in);
                    importTripleFile(scanner.nextLine());
                    fullProgram();
                case 2:
                    System.out.println("Input Triple:");
                    scanner = new Scanner(System.in);
                    try{
                        Importer.checkTriple(scanner.nextLine());
                    }
                    catch (Exception e) {
                    }
                    fullProgram();
                case 3:
                    System.out.println("Enter a query file path:");
                    scanner = new Scanner(System.in);
                    QueryEngine.executeQueryFile(scanner.nextLine());
                    fullProgram();
                case 4:
                    System.out.println("Enter query:");
                    scanner = new Scanner(System.in);
                    QueryEngine.executeQuery(scanner.nextLine());
                    fullProgram();
                case 5:
                    QueryEngine.executeQuery("? ? ?");
                    fullProgram();
                case 6:
                    option();
                default:
                    System.out.println("Sorry, incorrect entry");
                    option();
            }
        }
}

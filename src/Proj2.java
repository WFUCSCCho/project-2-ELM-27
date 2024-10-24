/*
 * @file: Proj2.java
 * @description: Follows instructions on what to run, as seen in
 *               README.
 *                  Times executions of insertion and searches of
 *                  sorted and unsorted BST and AVL Trees.
 * @author: Elliott Lowman
 * @date: October 24, 2024
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Proj2 {
    public static void main(String[] args) throws IOException {
        // Use command line arguments to specify the input file
        /*
        if (args.length != 2) {
            System.err.println("Usage: java TestAvl <input file> <number of lines>");
            System.exit(1);
        }
        */

        String inputFileName = args[0];
        int numLines = Integer.parseInt(args[1]);

        ArrayList<Catcher> catcherList = new ArrayList<Catcher>();
        BST<Catcher> catcherBST = new BST<>();
        AvlTree<Catcher> catcherAvlTree = new AvlTree<>();

        long totalTime;

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line
        inputFileNameScanner.nextLine();
	    // FINISH ME

        for(int i = 0; i < numLines; i++) {
            catcherList.add(new Catcher(inputFileNameScanner.nextLine()));
        }

        writeToFile(Integer.toString(numLines), "output.txt");

        // sorted insertion
        totalTime = System.nanoTime();
        for(int i = 0; i < numLines; i++) {
            catcherAvlTree.insert(catcherList.get(i));
        }
        totalTime = System.nanoTime() - totalTime;
        System.out.println("Sorted AVL Insertion for " + numLines +  " lines: " + (totalTime/1e9) + " seconds.");
        writeToFile(Float.toString(totalTime), "output.txt");
        totalTime = System.nanoTime();
        for(int i = 0; i < numLines; i++) {
            catcherBST.insert(catcherList.get(i));
        }
        totalTime = System.nanoTime() - totalTime;
        System.out.println("Sorted BST Insertion for " + numLines +  " lines: " + (totalTime/1e9) + " seconds.");
        writeToFile(Float.toString(totalTime), "output.txt");

        // sorted search
        totalTime = System.nanoTime();
        for(int i = 0; i < numLines; i++) {
            catcherAvlTree.contains(catcherList.get(i));
        }
        totalTime = System.nanoTime() - totalTime;
        System.out.println("Sorted AVL Search for " + numLines +  " lines: " + (totalTime/1e9) + " seconds.");
        writeToFile(Float.toString(totalTime), "output.txt");
        totalTime = System.nanoTime();
        for(int i = 0; i < numLines; i++) {
            catcherBST.search(catcherList.get(i));
        }
        totalTime = System.nanoTime() - totalTime;
        System.out.println("Sorted BST Search for " + numLines +  " lines: " + (totalTime/1e9) + " seconds.");
        writeToFile(Float.toString(totalTime), "output.txt");

        Collections.shuffle(catcherList);
        catcherAvlTree.makeEmpty();
        catcherBST.clear();

        // unsorted insertion
        totalTime = System.nanoTime();
        for(int i = 0; i < numLines; i++) {
            catcherAvlTree.insert(catcherList.get(i));
        }
        totalTime = System.nanoTime() - totalTime;
        System.out.println("Unsorted AVL Insertion for " + numLines +  " lines: " + (totalTime/1e9) + " seconds.");
        writeToFile(Float.toString(totalTime), "output.txt");
        totalTime = System.nanoTime();
        for(int i = 0; i < numLines; i++) {
            catcherBST.insert(catcherList.get(i));
        }
        totalTime = System.nanoTime() - totalTime;
        System.out.println("Unsorted BST Insertion for " + numLines +  " lines: " + (totalTime/1e9) + " seconds.");
        writeToFile(Float.toString(totalTime), "output.txt");

        Collections.sort(catcherList);

        // unsorted search
        totalTime = System.nanoTime();
        for(int i = 0; i < numLines; i++) {
            catcherAvlTree.contains(catcherList.get(i));
        }
        totalTime = System.nanoTime() - totalTime;
        System.out.println("Unsorted AVL Search for " + numLines +  " lines: " + (totalTime/1e9) + " seconds.");
        writeToFile(Float.toString(totalTime), "output.txt");
        totalTime = System.nanoTime();
        for(int i = 0; i < numLines; i++) {
            catcherBST.search(catcherList.get(i));
        }
        totalTime = System.nanoTime() - totalTime;
        System.out.println("Unsorted BST Search for " + numLines +  " lines: " + (totalTime/1e9) + " seconds.");
        fileNewLine(Float.toString(totalTime), "output.txt");
    }

    public static void writeToFile(String content, String filePath) throws IOException {
        FileWriter outFile = new FileWriter(filePath, true);  // navigates to end of file

        outFile.write(content + ",");

        outFile.close();
    }

    public static void fileNewLine(String content, String filePath) throws IOException {
        FileWriter outFile = new FileWriter(filePath, true);  // navigates to end of file

        outFile.write(content + "\n");

        outFile.close();
    }
}

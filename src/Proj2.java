import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Proj2 {
    public static void main(String[] args) throws IOException {
        /*
        // Use command line arguments to specify the input file
        if (args.length != 2) {
            System.err.println("Usage: java TestAvl <input file> <number of lines>");
            System.exit(1);
        }

         */

        String inputFileName = "C:\\Users\\Elliott Lowman\\Downloads\\Sophomore First Semester\\Data Structures\\project-2-ELM-27\\CTB_Data.csv"; // args[0];
        int numLines = 10;    // Integer.parseInt(args[1]);

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

        // sorted insertion
        totalTime = System.nanoTime();
        for(int i = 0; i < numLines; i++) {
            catcherAvlTree.insert(catcherList.get(i));
        }
        totalTime = System.nanoTime() - totalTime;
        System.out.println("Sorted AVL Insertion: " + (totalTime/1e9) + " seconds.");
        totalTime = System.nanoTime();
        for(int i = 0; i < numLines; i++) {
            catcherBST.insert(catcherList.get(i));
        }
        totalTime = System.nanoTime() - totalTime;
        System.out.println("Sorted BST Insertion: " + (totalTime/1e9) + " seconds.");

        // sorted search
        totalTime = System.nanoTime();
        for(int i = 0; i < numLines; i++) {
            catcherAvlTree.contains(catcherList.get(i));
        }
        totalTime = System.nanoTime() - totalTime;
        System.out.println("Sorted AVL Search: " + (totalTime/1e9) + " seconds.");
        totalTime = System.nanoTime();
        for(int i = 0; i < numLines; i++) {
            catcherBST.search(catcherList.get(i));
        }
        totalTime = System.nanoTime() - totalTime;
        System.out.println("Sorted BST Search: " + (totalTime/1e9) + " seconds.");

        Collections.shuffle(catcherList);

        // unsorted insertion
        totalTime = System.nanoTime();
        for(int i = 0; i < numLines; i++) {
            catcherAvlTree.insert(catcherList.get(i));
        }
        totalTime = System.nanoTime() - totalTime;
        System.out.println("Unsorted AVL Insertion: " + (totalTime/1e9) + " seconds.");
        totalTime = System.nanoTime();
        for(int i = 0; i < numLines; i++) {
            catcherBST.insert(catcherList.get(i));
        }
        totalTime = System.nanoTime() - totalTime;
        System.out.println("Unsorted BST Insertion: " + (totalTime/1e9) + " seconds.");

        // unsorted search
        totalTime = System.nanoTime();
        for(int i = 0; i < numLines; i++) {
            catcherAvlTree.contains(catcherList.get(i));
        }
        totalTime = System.nanoTime() - totalTime;
        System.out.println("Unsorted AVL Search: " + (totalTime/1e9) + " seconds.");
        totalTime = System.nanoTime();
        for(int i = 0; i < numLines; i++) {
            catcherBST.search(catcherList.get(i));
        }
        totalTime = System.nanoTime() - totalTime;
        System.out.println("Unsorted BST Search: " + (totalTime/1e9) + " seconds.");
    }
}

package avengers;

import java.util.*;

/**
 * Given a starting event and an Adjacency Matrix representing a graph of all
 * possible
 * events once Thanos arrives on Titan, determine the total possible number of
 * timelines
 * that could occur AND the number of timelines with a total Expected Utility
 * (EU) at
 * least the threshold value.
 * 
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * UseTimeStoneInputFile name is passed through the command line as args[0]
 * Read from UseTimeStoneInputFile with the format:
 * 1. t (int): expected utility (EU) threshold
 * 2. v (int): number of events (vertices in the graph)
 * 3. v lines, each with 2 values: (int) event number and (int) EU value
 * 4. v lines, each with v (int) edges: 1 means there is a direct edge between
 * two vertices, 0 no edge
 * 
 * Note 1: the last v lines of the UseTimeStoneInputFile is an ajacency matrix
 * for a directed
 * graph.
 * The rows represent the "from" vertex and the columns represent the "to"
 * vertex.
 * 
 * The matrix below has only two edges: (1) from vertex 1 to vertex 3 and, (2)
 * from vertex 2 to vertex 0
 * 0 0 0 0
 * 0 0 0 1
 * 1 0 0 0
 * 0 0 0 0
 * 
 * Step 2:
 * UseTimeStoneOutputFile name is passed through the command line as args[1]
 * Assume the starting event is vertex 0 (zero)
 * Compute all the possible timelines, output this number to the output file.
 * Compute all the posssible timelines with Expected Utility higher than the EU
 * threshold,
 * output this number to the output file.
 * 
 * Note 2: output these number the in above order, one per line.
 * 
 * Note 3: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 * To read from a file use StdIn:
 * StdIn.setFile(inputfilename);
 * StdIn.readInt();
 * StdIn.readDouble();
 * 
 * To write to a file use StdOut:
 * StdOut.setFile(outputfilename);
 * //Call StdOut.print() for total number of timelines
 * //Call StdOut.print() for number of timelines with EU >= threshold EU
 * 
 * Compiling and executing:
 * 1. Make sure you are in the ../InfinityWar directory
 * 2. javac -d bin src/avengers/*.java
 * 3. java -cp bin avengers/UseTimeStone usetimestone.in usetimestone.out
 * 
 * @author Yashas Ravi
 * 
 */

public class UseTimeStone {

    public static void main(String[] args) {

        if (args.length < 2) {
            StdOut.println("Execute: java UseTimeStone <INput file> <OUTput file>");
            return;
        }

        // WRITE YOUR CODE HERE

        // read file names from command line
        String timeStoneInputFile = args[0];
        String tmeStoneOutputFile = args[1];

        // Set the input file.
        StdIn.setFile(timeStoneInputFile);

        int threshold = StdIn.readInt();
        int nodes = StdIn.readInt();
        int[] eU = new int[nodes];
        int[][] matrix = new int[nodes][nodes];

        // Initalizes fuctionality list with input
        for (int i = 0; i < eU.length; i++) {
            eU[StdIn.readInt()] = StdIn.readInt();
        }

        // Intitalizes adjaceny matrix with input and updates matrix
        for (int j = 0; j < matrix.length; j++) {
            for (int k = 0; k < matrix.length; k++) {
                matrix[j][k] = StdIn.readInt();
            }
        }

        // arrayList for timeLine EUs
        ArrayList<Integer> timeLines = new ArrayList<Integer>();
        pathTraversalFromZero(matrix, eU, 0, 0, timeLines);
        int count = 0;
        for (int num : timeLines) {
            if (num >= threshold) {
                count++;
            }
        }

        // set output file
        StdOut.setFile(tmeStoneOutputFile);
        // println for num timelines and numPaths >= threshold
        StdOut.println(timeLines.size());
        StdOut.println(count);

    }

    private static void pathTraversalFromZero(int[][] matrixArr, int[] arrEU, int startRow, int total, ArrayList<Integer> timeL) {
        // base case row is all zero return to previous node and add
        int newTotal = total + arrEU[startRow];
        timeL.add(newTotal);
        if (!checkHasConnections(matrixArr, startRow)) {
            return;
        }
        // else contunue iterating until all possible things have been iterated over
        for (int i = 0; i < matrixArr[startRow].length; i++) {
            if (matrixArr[startRow][i] == 1) {
                pathTraversalFromZero(matrixArr, arrEU, i, newTotal, timeL);
            }
        }
    }

    private static boolean checkHasConnections(int[][] matrixArr, int row) {
        for (int i = 0; i < matrixArr[row].length; i++) {
            if (matrixArr[row][i] != 0) {
                return true;
            }
        }
        return false;
    }
}

package avengers;

/**
 * 
 * Using the Adjacency Matrix of n vertices and starting from Earth (vertex 0),
 * modify the edge weights using the functionality values of the vertices that
 * each edge
 * connects, and then determine the minimum cost to reach Titan (vertex n-1)
 * from Earth (vertex 0).
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * LocateTitanInputFile name is passed through the command line as args[0]
 * Read from LocateTitanInputFile with the format:
 * 1. g (int): number of generators (vertices in the graph)
 * 2. g lines, each with 2 values, (int) generator number, (double) funcionality
 * value
 * 3. g lines, each with g (int) edge values, referring to the energy cost to
 * travel from
 * one generator to another
 * Create an adjacency matrix for g generators.
 * 
 * Populate the adjacency matrix with edge values (the energy cost to travel
 * from one
 * generator to another).
 * 
 * Step 2:
 * Update the adjacency matrix to change EVERY edge weight (energy cost) by
 * DIVIDING it
 * by the functionality of BOTH vertices (generators) that the edge points to.
 * Then,
 * typecast this number to an integer (this is done to avoid precision errors).
 * The result
 * is an adjacency matrix representing the TOTAL COSTS to travel from one
 * generator to another.
 * 
 * Step 3:
 * LocateTitanOutputFile name is passed through the command line as args[1]
 * Use Dijkstra’s Algorithm to find the path of minimum cost between Earth and
 * Titan.
 * Output this number into your output file!
 * 
 * Note: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 * To read from a file use StdIn:
 * StdIn.setFile(inputfilename);
 * StdIn.readInt();
 * StdIn.readDouble();
 * 
 * To write to a file use StdOut (here, minCost represents the minimum cost to
 * travel from Earth to Titan):
 * StdOut.setFile(outputfilename);
 * StdOut.print(minCost);
 * 
 * Compiling and executing:
 * 1. Make sure you are in the ../InfinityWar directory
 * 2. javac -d bin src/avengers/*.java
 * 3. java -cp bin avengers/LocateTitan locatetitan.in locatetitan.out
 * 
 * @author Yashas Ravi
 * 
 */

public class LocateTitan {

    public static void main(String[] args) {

        /*if (args.length < 2) {
            StdOut.println("Execute: java LocateTitan <INput file> <OUTput file>");
            return;
        }*/

        // WRITE YOUR CODE HERE

        // read file names from command line
        String locateTitanInputFile = "locate";
        String locateTitanOutputFile = args[1];

        // Set the input file.
        StdIn.setFile(locateTitanInputFile);

        int nodes = StdIn.readInt();
        int[] minCost = new int[nodes];
        double[] funct = new double[nodes];
        boolean[] dijkstraSet = new boolean[nodes];
        int[][] matrix = new int[nodes][nodes];

        // Initalizes fuctionality list with input
        for (int i = 0; i < funct.length; i++) {
            funct[StdIn.readInt()] = StdIn.readDouble();
        }

        // Intitalizes adjaceny matrix with input and updates matrix
        for (int j = 0; j < matrix.length; j++) {
            for (int k = 0; k < matrix.length; k++) {
                matrix[j][k] = StdIn.readInt();
                if (matrix[j][k] > 0) {
                    matrix[j][k] = (int) (matrix[j][k] / (funct[j] * funct[k]));
                }
            }
        }

        // Dijkastra's Algo Start

        // set minCost Array to inf except 0
        for (int m = 0; m < minCost.length; m++) {
            if (m == 0) {
                minCost[m] = 0;
            } else {
                minCost[m] = Integer.MAX_VALUE;
            }
        }

        // path searching
        for (int n = 0; n < nodes - 1; n++) {
            // determins node with mincost and stroes it in currSource
            int currSource = 0;
            int currMinCost = Integer.MAX_VALUE;
            for (int i = 0; i < minCost.length; i++) {
                // if vertex i has not been traversed and less than the current min, set current
                // min to i
                if (!dijkstraSet[i] && minCost[i] < currMinCost) {
                    currSource = i;
                    currMinCost = minCost[currSource];
                }
            }

            // sets currSource as traversed in dijkaSet[]
            dijkstraSet[currSource] = true;

            // Relax all the nodes adjacent to the node we are visiting (currentSource)
            for (int t = (currSource + 1) % matrix[currSource].length; t != currSource; t = (t + 1)
                    % (matrix[currSource].length)) {
                // need to add total cost form 0 is < current cost
                int currCost = minCost[currSource] + matrix[currSource][t];
                if (matrix[currSource][t] != 0 && !dijkstraSet[t] && currSource != Integer.MAX_VALUE
                        && currCost < minCost[t]) {
                    minCost[t] = currCost;
                }
            }

        }

        // Set the output file.
        StdOut.setFile(locateTitanOutputFile);
        // WRITE YOUR CODE HERE TO OUTPUT TO THE OUTPUT FILE
        StdOut.print(minCost[minCost.length - 1]);
    }
}
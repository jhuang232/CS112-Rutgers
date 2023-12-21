package avengers;

import java.util.*;

/**
 * Given a Set of Edges representing Vision's Neural Network, identify all of
 * the
 * vertices that connect to the Mind Stone.
 * List the names of these neurons in the output file.
 * 
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * MindStoneNeighborNeuronsInputFile name is passed through the command line as
 * args[0]
 * Read from the MindStoneNeighborNeuronsInputFile with the format:
 * 1. v (int): number of neurons (vertices in the graph)
 * 2. v lines, each with a String referring to a neuron's name (vertex name)
 * 3. e (int): number of synapses (edges in the graph)
 * 4. e lines, each line refers to an edge, each line has 2 (two) Strings: from
 * to
 * 
 * Step 2:
 * MindStoneNeighborNeuronsOutputFile name is passed through the command line as
 * args[1]
 * Identify the vertices that connect to the Mind Stone vertex.
 * Output these vertices, one per line, to the output file.
 * 
 * Note 1: The Mind Stone vertex has out degree 0 (zero), meaning that there are
 * no edges leaving the vertex.
 * 
 * Note 2: If a vertex v connects to the Mind Stone vertex m then the graph has
 * an edge v -> m
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
 * //Call StdOut.print() for EVERY neuron (vertex) neighboring the Mind Stone
 * neuron (vertex)
 * 
 * Compiling and executing:
 * 1. Make sure you are in the ../InfinityWar directory
 * 2. javac -d bin src/avengers/*.java
 * 3. java -cp bin avengers/MindStoneNeighborNeurons mindstoneneighborneurons.in
 * mindstoneneighborneurons.out
 *
 * @author Yashas Ravi
 * 
 */

public class MindStoneNeighborNeurons {

    public static void main(String[] args) {

        if (args.length < 2) {
            StdOut.println("Execute: java MindStoneNeighborNeurons <INput file> <OUTputfile>");
            return;
        }

        // WRITE YOUR CODE HERE

        // read filenames from command line
        String mindStoneInputFile = args[0];
        String mindStoneOutputFile = args[1];

        // sets input file
        StdIn.setFile(mindStoneInputFile);

        // create array of vertecies
        int numOfVerticies = StdIn.readInt();
        String[] verticies = new String[numOfVerticies];
        for (int i = 0; i < numOfVerticies; i++) {
            verticies[i] = StdIn.readString();
        }

        // creates hash map of edges
        int numEdges = StdIn.readInt();
        HashMap<String, String> edges = new HashMap<>(numEdges);
        while (!StdIn.isEmpty()) {
            // for (int j = 0; j < edges.size(); j++) {
            edges.put(StdIn.readString(), StdIn.readString());
        }

        // sets output file
        StdOut.setFile(mindStoneOutputFile);
        // for each loop to get all element connects related to last item
        edges.forEach((edge, connection) -> {
            if (connection.equals(verticies[verticies.length - 1])) {
                StdOut.println(edge);
            }
        });

    }
}

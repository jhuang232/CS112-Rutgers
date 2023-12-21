package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class contains methods which, when used together, perform the
 * entire Huffman Coding encoding and decoding process
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class HuffmanCoding {
    private String fileName;
    private ArrayList<CharFreq> sortedCharFreqList;
    private TreeNode huffmanRoot;
    private String[] encodings;

    /**
     * Constructor used by the driver, sets filename
     * DO NOT EDIT
     * 
     * @param f The file we want to encode
     */
    public HuffmanCoding(String f) {
        fileName = f;
    }

    /**
     * Reads from filename character by character, and sets sortedCharFreqList
     * to a new ArrayList of CharFreq objects with frequency > 0, sorted by
     * frequency
     */
    public void makeSortedList() {
        StdIn.setFile(fileName);

        /* Your code goes here */
        sortedCharFreqList = new ArrayList<CharFreq>();
        // array to count frequency
        int[] numChar = new int[128];

        double totalNumOfChars = 0;

        // read file and update frequency
        while (StdIn.hasNextChar()) {
            char currChar = StdIn.readChar();
            numChar[(int) currChar] += 1;
            totalNumOfChars++;
        }

        // populates sortedCharFreqList with CharFreq objects from numChar
        for (int i = 0; i < numChar.length; i++) {
            if (numChar[i] > 0) {
                CharFreq item = new CharFreq((char) i, (numChar[i] / totalNumOfChars));
                sortedCharFreqList.add(item);
            }
        }

        // case for only 1 distinct character
        if (sortedCharFreqList.size() == 1) {
            int disCharASCII = (int) sortedCharFreqList.get(0).getCharacter();
            CharFreq holder = (disCharASCII == 127) ? new CharFreq((char) 0, 0)
                    : new CharFreq((char) (disCharASCII + 1), 0);
            sortedCharFreqList.add(holder);
        }

        // sort sortedCharFreqList
        Collections.sort(sortedCharFreqList);

    }

    /**
     * Uses sortedCharFreqList to build a huffman coding tree, and stores its root
     * in huffmanRoot
     */
    public void makeTree() {

        /* Your code goes here */
        // create two queues source and target
        Queue<TreeNode> source = new Queue<TreeNode>();
        Queue<TreeNode> target = new Queue<TreeNode>();

        // create node for each CharFreq element in sortedCharFreqList
        // enqeue node into source in increasing order
        for (int i = 0; i < sortedCharFreqList.size(); i++) {
            TreeNode ele = new TreeNode(sortedCharFreqList.get(i), null, null);
            source.enqueue(ele);
        }

        // repeat unitl scoure is empty and target only has one node
        while (!source.isEmpty() || (source.isEmpty() && target.size() != 1)) {
            // peek both and deqeue smallest, scorce has priority
            TreeNode node1 = getMinNode(source, target);
            TreeNode node2 = getMinNode(source, target);

            // make new treeNode(null, sum of two nodes) and set first to left, second to
            // right
            CharFreq sumFreq = new CharFreq(null, node1.getData().getProbOcc() + node2.getData().getProbOcc());
            TreeNode parNode = new TreeNode(sumFreq, node1, node2);
            // enque new node to target
            target.enqueue(parNode);
        }
        huffmanRoot = target.peek();
    }

    private TreeNode getMinNode(Queue<TreeNode> sor, Queue<TreeNode> targ) {
        if (targ.isEmpty() && !sor.isEmpty()) {
            return sor.dequeue();
        } else if (sor.isEmpty() && !targ.isEmpty()) {
            return targ.dequeue();
        } else {
            return (sor.peek().getData().getProbOcc() <= targ.peek().getData().getProbOcc()) ? sor.dequeue()
                    : targ.dequeue();
        }
    }

    /**
     * Uses huffmanRoot to create a string array of size 128, where each
     * index in the array contains that ASCII character's bitstring encoding.
     * Characters not
     * present in the huffman coding tree should have their spots in the array left
     * null.
     * Set encodings to this array.
     */
    public void makeEncodings() {

        /* Your code goes here */
        encodings = new String[128];
        String encoding = "";
        getEncoding(huffmanRoot, encoding);

    }

    private void getEncoding(TreeNode parent, String encoded) {
        // String encoding = "";
        if (parent.getLeft() == null && parent.getRight() == null) {
            encodings[(int) parent.getData().getCharacter()] = encoded;
        }
        if (parent.getLeft() != null) {
            getEncoding(parent.getLeft(), encoded + "0");
        }
        if (parent.getRight() != null) {
            getEncoding(parent.getRight(), encoded + "1");
        }
    }

    /**
     * Using encodings and filename, this method makes use of the writeBitString
     * method
     * to write the final encoding of 1's and 0's to the encoded file.
     * 
     * @param encodedFile The file name into which the text file is to be encoded
     */
    public void encode(String encodedFile) {
        StdIn.setFile(fileName);

        /* Your code goes here */
        String encodedString = ""; // may cause extra bit
        while (StdIn.hasNextChar()) {
            encodedString += encodings[(int) StdIn.readChar()];
        }

        writeBitString(encodedFile, encodedString);
    }

    /**
     * Writes a given string of 1's and 0's to the given file byte by byte
     * and NOT as characters of 1 and 0 which take up 8 bits each
     * DO NOT EDIT
     * 
     * @param filename  The file to write to (doesn't need to exist yet)
     * @param bitString The string of 1's and 0's to write to the file in bits
     */
    public static void writeBitString(String filename, String bitString) {
        byte[] bytes = new byte[bitString.length() / 8 + 1];
        int bytesIndex = 0, byteIndex = 0, currentByte = 0;

        // Pad the string with initial zeroes and then a one in order to bring
        // its length to a multiple of 8. When reading, the 1 signifies the
        // end of padding.
        int padding = 8 - (bitString.length() % 8);
        String pad = "";
        for (int i = 0; i < padding - 1; i++)
            pad = pad + "0";
        pad = pad + "1";
        bitString = pad + bitString;

        // For every bit, add it to the right spot in the corresponding byte,
        // and store bytes in the array when finished
        for (char c : bitString.toCharArray()) {
            if (c != '1' && c != '0') {
                System.out.println("Invalid characters in bitstring");
                return;
            }

            if (c == '1')
                currentByte += 1 << (7 - byteIndex);
            byteIndex++;

            if (byteIndex == 8) {
                bytes[bytesIndex] = (byte) currentByte;
                bytesIndex++;
                currentByte = 0;
                byteIndex = 0;
            }
        }

        // Write the array of bytes to the provided file
        try {
            FileOutputStream out = new FileOutputStream(filename);
            out.write(bytes);
            out.close();
        } catch (Exception e) {
            System.err.println("Error when writing to file!");
        }
    }

    /**
     * Using a given encoded file name, this method makes use of the readBitString
     * method
     * to convert the file into a bit string, then decodes the bit string using the
     * tree, and writes it to a decoded file.
     * 
     * @param encodedFile The file which has already been encoded by encode()
     * @param decodedFile The name of the new file we want to decode into
     */
    public void decode(String encodedFile, String decodedFile) {
        StdOut.setFile(decodedFile);

        /* Your code goes here */
        String bitString = readBitString(encodedFile);
        String decoded = "";
        TreeNode curr = huffmanRoot;

        for (int i = 0; i < bitString.length(); i++) {
            if (curr.getLeft() == null && curr.getRight() == null) {
                decoded += curr.getData().getCharacter();
                curr = huffmanRoot;
            }
            if (bitString.charAt(i) == '1') {
                curr = curr.getRight();
            } else if (bitString.charAt(i) == '0') {
                curr = curr.getLeft();
            }
        }
        // last char
        if (curr.getLeft() == null && curr.getRight() == null) {
            decoded += curr.getData().getCharacter();
            curr = huffmanRoot;
        }
        StdOut.print(decoded);
    }

    /**
     * Reads a given file byte by byte, and returns a string of 1's and 0's
     * representing the bits in the file
     * DO NOT EDIT
     * 
     * @param filename The encoded file to read from
     * @return String of 1's and 0's representing the bits in the file
     */
    public static String readBitString(String filename) {
        String bitString = "";

        try {
            FileInputStream in = new FileInputStream(filename);
            File file = new File(filename);

            byte bytes[] = new byte[(int) file.length()];
            in.read(bytes);
            in.close();

            // For each byte read, convert it to a binary string of length 8 and add it
            // to the bit string
            for (byte b : bytes) {
                bitString = bitString +
                        String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            }

            // Detect the first 1 signifying the end of padding, then remove the first few
            // characters, including the 1
            for (int i = 0; i < 8; i++) {
                if (bitString.charAt(i) == '1')
                    return bitString.substring(i + 1);
            }

            return bitString.substring(8);
        } catch (Exception e) {
            System.out.println("Error while reading file!");
            return "";
        }
    }

    /*
     * Getters used by the driver.
     * DO NOT EDIT or REMOVE
     */

    public String getFileName() {
        return fileName;
    }

    public ArrayList<CharFreq> getSortedCharFreqList() {
        return sortedCharFreqList;
    }

    public TreeNode getHuffmanRoot() {
        return huffmanRoot;
    }

    public String[] getEncodings() {
        return encodings;
    }
}

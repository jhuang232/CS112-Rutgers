package conwaygame;

import java.util.ArrayList;

import javax.lang.model.type.UnionType;

/**
 * Conway's Game of Life Class holds various methods that will
 * progress the state of the game's board through it's many
 * iterations/generations.
 *
 * Rules
 * Alive cells with 0-1 neighbors die of loneliness.
 * Alive cells with >=4 neighbors die of overpopulation.
 * Alive cells with 2-3 neighbors survive.
 * Dead cells with exactly 3 neighbors become alive by reproduction.
 * 
 * @author Seth Kelley
 * @author Maxwell Goldberg
 */
public class GameOfLife {

    // Instance variables
    private static final boolean ALIVE = true;
    private static final boolean DEAD = false;

    private boolean[][] grid; // The board has the current generation of cells
    private int totalAliveCells; // Total number of alive cells in the grid (board)

    /**
     * Default Constructor which creates a small 5x5 grid with five alive cells.
     * This variation does not exceed bounds and dies off after four iterations.
     */
    public GameOfLife() {
        grid = new boolean[5][5];
        totalAliveCells = 5;
        grid[1][1] = ALIVE;
        grid[1][3] = ALIVE;
        grid[2][2] = ALIVE;
        grid[3][2] = ALIVE;
        grid[3][3] = ALIVE;
    }

    /**
     * Constructor used that will take in values to create a grid with a given
     * number
     * of alive cells
     * 
     * @param file is the input file with the initial game pattern formatted as
     *             follows:
     *             An integer representing the number of grid rows, say r
     *             An integer representing the number of grid columns, say c
     *             Number of r lines, each containing c true or false values (true
     *             denotes an ALIVE cell)
     */
    public GameOfLife(String file) {
        StdIn.setFile(file);
        while (!StdIn.isEmpty()) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            grid = new boolean[row][col];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    grid[i][j] = StdIn.readBoolean();
                    if (grid[i][j]) {
                        totalAliveCells++;
                    }
                }
            }
        }
    }

    /**
     * Returns grid
     * 
     * @return boolean[][] for current grid
     */
    public boolean[][] getGrid() {
        return grid;
    }

    /**
     * Returns totalAliveCells
     * 
     * @return int for total number of alive cells in grid
     */
    public int getTotalAliveCells() {
        return totalAliveCells;
    }

    /**
     * Returns the status of the cell at (row,col): ALIVE or DEAD
     * 
     * @param row row position of the cell
     * @param col column position of the cell
     * @return true or false value "ALIVE" or "DEAD" (state of the cell)
     */
    public boolean getCellState(int row, int col) {

        // WRITE YOUR CODE HERE
        return grid[row][col]; // update this line, provided so that code compiles
    }

    /**
     * Returns true if there are any alive cells in the grid
     * 
     * @return true if there is at least one cell alive, otherwise returns false
     */
    public boolean isAlive() {

        // WRITE YOUR CODE HERE
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == true) {
                    return true;
                }
            }
        }
        return false; // update this line, provided so that code compiles
    }

    /**
     * Determines the number of alive cells around a given cell.
     * Each cell has 8 neighbor cells which are the cells that are
     * horizontally, vertically, or diagonally adjacent.
     * 
     * @param col column position of the cell
     * @param row row position of the cell
     * @return neighboringCells, the number of alive cells (at most 8).
     */
    public int numOfAliveNeighbors(int row, int col) {

        // WRITE YOUR CODE HERE
        int numAlive = 0;
        int r1 = row - 1;
        int c1 = col - 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (r1 < 0) {
                    r1 = grid.length - 1;
                } else if (r1 > grid.length - 1) {
                    r1 = 0;
                }
                if (c1 < 0) {
                    c1 = grid[0].length - 1;
                } else if (c1 > grid[0].length - 1) {
                    c1 = 0;
                }
                if (grid[r1][c1] && !(r1 == row && c1 == col)) {
                    numAlive++;
                }
                c1++;
                // System.out.println(r1 + "," + c1);
            }
            r1++;
            c1 = col - 1;
        }
        return numAlive; // update this line, provided so that code compiles
    }

    /**
     * Creates a new grid with the next generation of the current grid using
     * the rules for Conway's Game of Life.
     * 
     * @return boolean[][] of new grid (this is a new 2D array)
     */
    public boolean[][] computeNewGrid() {

        // WRITE YOUR CODE HERE
        boolean[][] newGrid = new boolean[grid.length][grid[0].length];
        // totalAliveCells = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] && numOfAliveNeighbors(i, j) < 2) {
                    newGrid[i][j] = false;
                } else if (!grid[i][j] && numOfAliveNeighbors(i, j) == 3) {
                    newGrid[i][j] = true;
                    totalAliveCells++;
                } else if (grid[i][j] && (numOfAliveNeighbors(i, j) == 3 || numOfAliveNeighbors(i, j) == 2)) {
                    newGrid[i][j] = true;
                    totalAliveCells++;
                } else if (grid[i][j] && numOfAliveNeighbors(i, j) >= 4) {
                    newGrid[i][j] = false;
                }

            }
        }
        // System.out.println(totalAliveCells);
        return newGrid;// update this line, provided so that code compiles
    }

    /**
     * Updates the current grid (the grid instance variable) with the grid denoting
     * the next generation of cells computed by computeNewGrid().
     * 
     * Updates totalAliveCells instance variable
     */
    public void nextGeneration() {
        boolean[][] next = computeNewGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = next[i][j];
            }
        }
        // WRITE YOUR CODE HERE
    }

    /**
     * Updates the current grid with the grid computed after multiple (n)
     * generations.
     * 
     * @param n number of iterations that the grid will go through to compute a new
     *          grid
     */
    public void nextGeneration(int n) {

        for (int i = 0; i < n; i++) {
            nextGeneration();
        }
        // WRITE YOUR CODE HERE
    }

    /**
     * Determines the number of separate cell communities in the grid
     * 
     * @return the number of communities in the grid, communities can be formed from
     *         edges
     */
    public int numOfCommunities() {
        // WRITE YOUR CODE HERE
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(grid.length, grid[0].length);
        int count = 0;
        for (int p = 0; p < grid.length; p++) {
            for (int q = 0; q < grid[0].length; q++) {
                if (grid[p][q] && numOfAliveNeighbors(p, q) > 0) {
                    // add all alive neightbors to disjoint set if find(i, j) returns null/false
                    int r1 = p - 1;
                    int c1 = q - 1;
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            if (r1 < 0) {
                                r1 = grid.length - 1;
                            } else if (r1 > grid.length - 1) {
                                r1 = 0;
                            }
                            if (c1 < 0) {
                                c1 = grid[0].length - 1;
                            } else if (c1 > grid[0].length - 1) {
                                c1 = 0;
                            }
                            if (grid[r1][c1] && !(r1 == p && c1 == q)) {
                                uf.union(p, q, r1, c1);
                            }
                            c1++;
                            // System.out.println(r1 + "," + c1);
                        }
                        r1++;
                        c1 = q - 1;
                    }
                }
            }
        }
        ArrayList<Integer> roots = new ArrayList<Integer>();
        for (int o = 0; o < grid.length; o++) {
            for (int h = 0; h < grid[0].length; h++) {

                if (grid[o][h] && !find(roots, uf.find(o, h))) {
                    count++;
                    // parent doesent change;
                    roots.add(uf.find(o, h));
                }

            }
        }
        return count; // update this line, provided so that code compiles
    }

    private boolean find(ArrayList<Integer> arr, int val) {
        for (int i = 0; i < arr.size(); i++) {
            if (val == arr.get(i)) {
                return true;
            }
        }
        return false;
    }
}

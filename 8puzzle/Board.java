public class Board {
    public Board(int[][] tiles)            // create a board from an n-by-n array of tiles,
                                           // where tiles[row][col] = tile at (row, col)
    public String toString()               // string representation of this board
    public int tileAt(int row, int col)    // tile at (row, col) or 0 if blank
    public int size()                      // board size n
    public int hamming()                   // number of tiles out of place
    public int manhattan()                 // sum of Manhattan distances between tiles and goal
    public boolean isGoal()                // is this board the goal board?
    public boolean equals(Object y)        // does this board equal y?
    public Iterable<Board> neighbors()     // all neighboring boards
    public boolean isSolvable()            // is this board solvable?

    public static void main(String[] args) // unit testing (required)
}

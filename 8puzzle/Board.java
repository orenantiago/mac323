import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import java.lang.StringBuilder;
import java.lang.IllegalArgumentException;
import java.util.Iterator;

public class Board {
    private int n;
    private int [][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = new int [n][n];
        for(int row = 0; row < this.n; row++) {
            for(int col = 0; col < this.n; col++) {
                this.tiles[row][col] = tiles[row][col];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n);
        for(int row = 0; row < n; row++) {
            sb.append("\n");
            for(int col = 0; col < n; col++) {
                sb.append(" ");
                sb.append(tiles[row][col]);
            }
        }
        return sb.toString();
    }

    // tile at (row, col) or 0 if blank
    public int tileAt(int row, int col) {
        if(row < 0 || col < 0 || row >= n || col >= n)
            throw new IllegalArgumentException();
        return this.tiles[row][col];
    }

    // board size n
    public int size() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int position = 1;
        int outOfPlace = -1;

        for(int row = 0; row < n; row++) {
            for(int col = 0; col < n; col++) {
                if(tiles[row][col] != position) {
                    outOfPlace++;
                }
                position++;
            }
        }
        return outOfPlace;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int total = 0, desiredRow, desiredCol;

        for(int row = 0; row < n; row++) {
            for(int col = 0; col < n; col++) {
                if(tiles[row][col] != 0) {
                    desiredRow = (tiles[row][col] - 1) / n;
                    desiredCol = (tiles[row][col] - 1) % n;
                    total += difference(row, desiredRow);
                    total += difference(col, desiredCol);
                }
            }
        }
        return total;
    }

    private int difference(int x, int y) {
        if(x < y)
            return y - x;
        return x - y;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        Board b = (Board) y;
        if(b.size() != n)
            return false;

        for(int row = 0; row < n; row++)
            for(int col = 0; col < n; col++)
                if(b.tileAt(row, col) != tiles[row][col])
                    return false;

        return true;
    }

    // is this board solvable?
    public boolean isSolvable() {
        return isSolvableForParity(n % 2);
    }

    private boolean isSolvableForParity(int parity) {
        int inversions = 0;
        int blankRow = 0;
        int aux;
        for(int row1 = 0; row1 < n; row1++) {
            for(int col1 = 0; col1 < n; col1++) {
                for(int row2 = row1; row2 < n; row2++) {
                    aux = row2 == row1 ? col1 + 1 : 0;
                    for(int col2 = aux; col2 < n; col2++) {
                        if(tiles[row1][col1] != 0 && tiles[row2][col2] != 0 &&
                                tiles[row1][col1] > tiles[row2][col2]) {
                            inversions++;
                        }
                        if(tiles[row1][col1] == 0) {
                            blankRow = row1;
                        }
                    }
                }
            }
        }
        if(parity == 0)
            return (inversions+blankRow) % 2 != 0;
        else
            return inversions % 2 == 0;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new NeighborsIterable(this.tiles);
    }

    public class NeighborsIterable implements Iterable<Board> {
        private int [][] tiles;
        public NeighborsIterable(int [][]tiles) {
            this.tiles = tiles;
        }
        public NeighborsIterator iterator() {
            return new NeighborsIterator(tiles);
        }
    }

    public class NeighborsIterator implements Iterator<Board> {
        private int [][] tiles;
        private int emptyRow, emptyCol, size, movement;
        private Board[] boards;

        public NeighborsIterator(int[][]tiles) {
            size = 0;
            movement = 0;
            this.tiles = new int[tiles.length][tiles.length];
            for(int row = 0; row < tiles.length; row++) {
                for(int col = 0; col < tiles.length; col++) {
                    if(tiles[row][col] == 0) {
                        emptyRow = row;
                        emptyCol = col;
                    }
                    this.tiles[row][col] = tiles[row][col];
                }
            }
            populateArray();
        }

        private void populateArray() {
            boards = new Board[4];
            if(emptyCol - 1 >= 0) {
                move(emptyRow, emptyCol - 1);
                boards[size++] = new Board(this.tiles);
                move(emptyRow, emptyCol - 1);
            }
            if(emptyRow - 1 >= 0) {
                move(emptyRow - 1, emptyCol);
                boards[size++] = new Board(this.tiles);
                move(emptyRow - 1, emptyCol);
            }
            if(emptyCol + 1 < n) {
                move(emptyRow, emptyCol + 1);
                boards[size++] = new Board(this.tiles);
                move(emptyRow, emptyCol + 1);
            }
            if(emptyRow + 1 < n) {
                move(emptyRow + 1, emptyCol);
                boards[size++] = new Board(this.tiles);
                move(emptyRow + 1, emptyCol);
            }
        }

        public boolean hasNext() {
            return movement < size;
        }

        public Board next() {
            return boards[movement++];
        }
        private void move(int row, int col) {
            int aux = this.tiles[emptyRow][emptyCol];
            this.tiles[emptyRow][emptyCol] = this.tiles[row][col];
            this.tiles[row][col] = aux;
        }
    }

    // unit testing
    public static void main(String[] args) {
        int [][] tiles = {{5,8,7},{4,0,6},{1,2,3}};
        Board b = new Board(tiles);
        StdOut.println(b);
        StdOut.println("tile at 0,0 = " + b.tileAt(0, 0));
        StdOut.println(b.hamming() + " tiles out of position");
        StdOut.println("manhattan distance = " + b.manhattan());
        try {
            b.tileAt(66,66);
        } catch(IllegalArgumentException e) {
            StdOut.println("catch corner case on tileAt function");
        }

        int[][] tiles2 = {{1,2,3},{4,5,6},{7,8,0}};
        Board b1 = new Board(tiles2);
        Board b2 = new Board(tiles2);
        StdOut.println(b1);
        StdOut.println(b1.hamming() + " tiles out of position");
        StdOut.println("manhattan distance = " + b1.manhattan());

        StdOut.println("b should not equal b1 " + !b.equals(b1));
        StdOut.println("b2 should equal b1 " + b2.equals(b1));
        StdOut.println(b);
        for(Board neighbor : b.neighbors()) {
            StdOut.println(neighbor);
        }
        StdOut.println("b should not be solvable " + !b.isSolvable());
        StdOut.println("b2 should be solvable " + b2.isSolvable());
    }
}

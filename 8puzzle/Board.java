import java.lang.StringBuilder;
import java.lang.IllegalArgumentException;
import java.util.Iterator;

public class Board {
    private int n;
    private int [][] tiles;
    private int manhattan;

    public Board(int[][] tiles) {
        int total = 0, desiredRow, desiredCol;
        this.n = tiles.length;
        this.tiles = new int [n][n];
        for(int row = 0; row < this.n; row++) {
            for(int col = 0; col < this.n; col++) {
                this.tiles[row][col] = tiles[row][col];
                if(this.tiles[row][col] != 0) {
                    desiredRow = (this.tiles[row][col] - 1) / n;
                    desiredCol = (this.tiles[row][col] - 1) % n;
                    total += difference(row, desiredRow);
                    total += difference(col, desiredCol);
                }
            }
        }
        manhattan = total;
    }

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

    public int tileAt(int row, int col) {
        if(row < 0 || col < 0 || row >= n || col >= n)
            throw new IllegalArgumentException();
        return this.tiles[row][col];
    }

    public int size() {
        return n;
    }

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

    public int manhattan() {
        return manhattan;
    }

    private int difference(int x, int y) {
        if(x < y)
            return y - x;
        return x - y;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

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

    public boolean isSolvable() {
        return isSolvableForParity(n % 2);
    }

    private boolean isSolvableForParity(int parity) {
        int inversions = 0;
        int blankRow = 0;
        int aux;
        for(int row1 = 0; row1 < n; row1++) {
            for(int col1 = 0; col1 < n; col1++) {
                if(tiles[row1][col1] == 0) {
                    blankRow = row1;
                }
                for(int row2 = row1; row2 < n; row2++) {
                    aux = row2 == row1 ? col1 + 1 : 0;
                    for(int col2 = aux; col2 < n; col2++) {
                        if(tiles[row1][col1] != 0 && tiles[row2][col2] != 0 &&
                                tiles[row1][col1] > tiles[row2][col2]) {
                            inversions++;
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
}

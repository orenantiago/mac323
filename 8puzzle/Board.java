import edu.princeton.cs.algs4.StdOut;
import java.lang.StringBuilder;
import java.lang.IllegalArgumentException;

public class Board {
  private int n;
  private int [][] tiles;

  // create a board from an n-by-n array of tiles,
  // where tiles[row][col] = tile at (row, col)
  public Board(int[][] tiles) {
    this.tiles = tiles;
    this.n = tiles.length;
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

  public int tileAt(int row, int col) {
    if(row < 0 || col < 0 || row >= n || col >= n)
      throw new IllegalArgumentException();
    return this.tiles[row][col];
  }

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

  public boolean isGoal() {
    return hamming() == 0;
  }
  // public boolean equals(Object y)        // does this board equal y?
  // public Iterable<Board> neighbors()     // all neighboring boards
  // public boolean isSolvable()            // is this board solvable?

  public static void main(String[] args) {
    int [][] tiles = {{0,8,7},{4,5,6},{1,2,3}};
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
    StdOut.println(b1);
    StdOut.println(b1.hamming() + " tiles out of position");
    StdOut.println("manhattan distance = " + b1.manhattan());
  }
}

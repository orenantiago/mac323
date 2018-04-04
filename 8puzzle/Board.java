import edu.princeton.cs.algs4.StdOut;
import java.lang.StringBuilder;

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
        sb.append(tiles[row][col]);
        sb.append(" ");
      }
    }
    return sb.toString();
  }

  public int tileAt(int row, int col) {
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
  // public int manhattan()                 // sum of Manhattan distances between tiles and goal
  public boolean isGoal() {
    return hamming == 0;
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
  }
}

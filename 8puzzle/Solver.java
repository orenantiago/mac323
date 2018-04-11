import edu.princeton.cs.algs4.StdOut;
public class Solver {
  private static class MinPQ {
    private int n = 0;
    private Board [] items;

    private MinPQ() {
      items = new Board[10];
    }

    private void insert(Board b) {
      if(n == items.length - 1)
        resize();

      items[++n] = b;
      swim(n);
    }

    private Board get() {
      if(isEmpty())
        return null;
      Board min = items[1];
      items[1] = items[n--];
      sink(1);
      items[n + 1] = null;

      return min;
    }

    private void sink(int p) {
      while(2*p <= n) {
        int f = 2*p;
        if(f < n && less(f+1, f)) f++;
        if(less(p, f)) break;
        exch(p, f);
        p = f;
      }
    }

    private boolean isEmpty() {
      return n == 0;
    }

    private void resize() {
      Board[] aux = new Board[items.length * 2];
      for(int i = 1; i <= n; i++)
        aux[i] = items[i];
      items = aux;
    }

    private void swim(int n) {
      while(n > 1 && less(n, n/2)) {
        exch(n, n/2);
        n /= 2;
      }
    }

    private boolean less(int a, int b) {
      return items[a].manhattan() < items[b].manhattan();
    }

    private void exch(int a, int b) {
      Board aux = items[a];
      items[a] = items[b];
      items[b] = aux;
    }
  }
  public static void main(String[] args) {
    int [][] tiles = {{5,8,7},{4,0,6},{1,2,3}};
    Board board = new Board(tiles);
    MinPQ pq = new MinPQ();
    pq.insert(board);
    for(Board neighbor : board.neighbors()) {
      pq.insert(neighbor);
    }
    while(!pq.isEmpty())
      StdOut.println(pq.get());
  }
}

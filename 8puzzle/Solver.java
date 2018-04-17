import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    private MinPQ<SearchNode> boards;
    private SearchNode solution;

    private class SearchNode implements Comparable <SearchNode> {
        private Board board;
        private SearchNode previous;
        private int moves;

        private SearchNode(Board board, SearchNode previous, int moves) {
            this.board = board;
            this.previous = previous;
            this.moves = moves;
        }

        public int compareTo(SearchNode node) {
            int their = node.board.manhattan() + node.moves;
            int ours = this.board.manhattan() + this.moves;
            if(their > ours) return -1;
            if(their == ours) return 0;
            return 1;
        }

        private boolean isGoal() {
            return board.isGoal();
        }
    }

    public Solver(Board initial) {
        if(initial == null || !initial.isSolvable())
            throw new IllegalArgumentException();

        if(initial.isSolvable()) {
            boards = new MinPQ<>();
            SearchNode auxNode;
            SearchNode currentNode = new SearchNode(initial, null, 0);
            boards.insert(currentNode);
            while(!currentNode.isGoal()) {
                currentNode = boards.delMin();
                int moves = currentNode.moves + 1;
                for(Board nextBoard : currentNode.board.neighbors()) {
                    if(currentNode.previous == null || !nextBoard.equals(currentNode.previous.board)) {
                        auxNode = new SearchNode(nextBoard, currentNode, moves);
                        if(auxNode.isGoal()) {
                            currentNode = auxNode;
                            break;
                        }
                        boards.insert(auxNode);
                    }
                }
            }
            solution = currentNode;
        }
    }

     public int moves() {
        return solution.moves;
     }
     public Iterable<Board> solution() {
         Stack<Board> solutionStack = new Stack<>();
         solutionStack.push(solution.board);
         while(solution.previous != null) {
             solution = solution.previous;
             solutionStack.push(solution.board);
         }
         return solutionStack;
     }
}

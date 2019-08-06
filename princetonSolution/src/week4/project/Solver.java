package week4.project;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Collections;
import java.util.LinkedList;

public class Solver {
    private class Node implements Comparable<Node> {
        private final Board board;
        private final int move;
        private final int manhattan;
        private final Node prev;

        public Node(Board board, int move, Node prev) {
            this.board = board;
            this.manhattan = board.manhattan();
            this.move = move;
            this.prev = prev;
        }

        @Override
        public int compareTo(Node that) {
            int priority = manhattan + move;
            int thatPriority = that.manhattan + that.move;
            if (priority == thatPriority) return 0;
            return priority < thatPriority ? -1 : 1;
        }
    }

    private final boolean isSolvable;
    private final LinkedList<Board> solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("input should not be null");
        }
        Node origin = new Node(initial, 0, null);
        Node twin = new Node(initial.twin(), 0, null);
        solution = new LinkedList<>();
        MinPQ<Node> pqOri = new MinPQ<>();
        MinPQ<Node> pqTwin = new MinPQ<>();
        pqOri.insert(origin);
        pqTwin.insert(twin);
        while (!pqOri.isEmpty()) {
            Node curOri = pqOri.delMin(), curTwin = pqTwin.delMin();
            if (curOri.board.isGoal()) {
                fillSolution(curOri);
                isSolvable = true;
                return;
            }
            if (curTwin.board.isGoal()) {
                isSolvable = false;
                return;
            }
            for (Board nei : curOri.board.neighbors()) {
                if (curOri.prev != null && nei.equals(curOri.prev.board)) {
                    continue;
                }
                pqOri.insert(new Node(nei, curOri.move + 1, curOri));
            }
            for (Board nei : curTwin.board.neighbors()) {
                if (curTwin.prev != null && nei.equals(curTwin.prev.board)) {
                    continue;
                }
                pqTwin.insert(new Node(nei, curTwin.move + 1, curTwin));
            }
        }
        isSolvable = false;
        throw new IllegalStateException("i v a");
    }

    private void fillSolution(Node curNode) {
        while (curNode != null) {
            solution.addFirst(curNode.board);
            curNode = curNode.prev;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        return solution.size() - 1;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (!isSolvable)
            return null;
        return Collections.unmodifiableList(solution);
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}

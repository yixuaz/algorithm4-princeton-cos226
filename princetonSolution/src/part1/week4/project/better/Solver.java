package part1.week4.project.better;

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
            if (priority == thatPriority) return Integer.compare(manhattan, that.manhattan);
            return priority < thatPriority ? -1 : 1;
        }
    }

    private final Node solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("input should not be null");
        }
        Node origin = new Node(initial, 0, null);
        Node twin = new Node(initial.twin(), 0, null);
        MinPQ<Node> pq = new MinPQ<>();
        pq.insert(origin);
        pq.insert(twin);
        while (!pq.isEmpty()) {
            Node cur = pq.delMin();
            if (cur.board.isGoal()) {
                solution = fillSolution(cur, initial);
                return;
            }
            for (Board nei : cur.board.neighbors()) {
                if (cur.prev != null && nei.equals(cur.prev.board)) {
                    continue;
                }
                pq.insert(new Node(nei, cur.move + 1, cur));
            }
        }
        throw new IllegalStateException("invalid area");
    }

    private Node fillSolution(Node curNode, Board initial) {
        Node p = curNode;
        while (p.prev != null) {
            p = p.prev;
        }
        if (p.board == initial) {
            return curNode;
        }
        return null;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solution != null;
    }

    // min number of moves to solve initial board
    public int moves() {
        if (solution == null) return -1;
        return solution.move;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (solution == null) return null;
        LinkedList<Board> st = new LinkedList<>();
        Node p = solution;
        while (p != null) {
            st.addFirst(p.board);
            p = p.prev;
        }
        return Collections.unmodifiableList(st);
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

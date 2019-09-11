package part1.week4.project.better;

import part1.week4.project.better.heuristic.HeuristicFunction;
import part1.week4.project.better.heuristic.LinearConflict;
import part1.week4.project.better.heuristic.PatternDatabase;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;

public class IDAStarSolver {
    private HeuristicFunction heuristicAlgo = new LinearConflict();
    private class Node {
        private final Board board;
        private final int move;
        private final int manhattan;
        private final Node prev;

        public Node(Board board, int move, Node prev) {
            this.board = board;
            this.manhattan = heuristicAlgo.calculate(board.tiles, board.manhattan());
            this.move = move;
            this.prev = prev;
        }
    }

    private Node solution = null;

    public IDAStarSolver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("input should not be null");
        }
        if (!checkSolvable(initial)) {
            return;
        }
        Node init = new Node(initial, 0, null);
        int lowBound = init.manhattan;
        while (lowBound < 100 && solution == null) {
            lowBound = dfs(init, lowBound);
        }
    }

    private boolean checkSolvable(Board initial) {
        char[] tiles = hackGetTiles(initial);
        int invCnt = 0, yIdxFromBottom = 0, n = (int) Math.sqrt(tiles.length);
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == 0) {
                yIdxFromBottom = (n - i / n);
                continue;
            }
            for (int j = i + 1; j < tiles.length; j++) {
                // count pairs(i, j) such that i appears; before j, but i > j.
                if (tiles[j] == 0) continue;
                if (tiles[i] > tiles[j])
                    invCnt++;
            }
        }
        if (tiles.length % 2 == 1) {
            return invCnt % 2 == 0;
        } else {
            if (yIdxFromBottom % 2 == 1) return invCnt % 2 == 0;
            else return invCnt % 2 == 1;
        }
    }

    private int dfs(Node cur, int maxStep) {
        if (cur.board.isGoal() || solution != null) {
            if (solution == null)
                solution = cur;
            return cur.move;
        }
        if (cur.move + cur.manhattan > maxStep)
            return cur.move + cur.manhattan;
        int nowBound = Integer.MAX_VALUE;
        for (Board neighbor : cur.board.neighbors()) {
            if (cur.prev != null && neighbor.equals(cur.prev.board)) {
                continue;
            }
            int nextBound = dfs(new Node(neighbor, cur.move + 1, cur), maxStep);
            nowBound = Math.min(nextBound, nowBound);
            if (solution != null)
                return nowBound;
        }
        return nowBound;
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

    private char[] hackGetTiles(Board board) {
        try {
            Field field = board.getClass().getDeclaredField("tiles");
            field.setAccessible(true);
            return (char[]) field.get(board);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("failed to get char[] tiles in board", e);
        }
    }


}

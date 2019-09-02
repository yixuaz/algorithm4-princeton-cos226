package part2.week5.datacompression;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.PriorityQueue;

public class TernaryHuffmanCodes {
    private static final int R = 256;

    private TernaryHuffmanCodes() { }

    private static class Node implements Comparable<Node> {
        private final char ch;
        private final int freq;
        private final Node left, mid, right;

        Node(char ch, int freq, Node left, Node mid, Node right) {
            this.ch    = ch;
            this.freq  = freq;
            this.left  = left;
            this.mid = mid;
            this.right = right;
        }

        private boolean isLeaf() {
            assert ((left == null) && (right == null) && (mid == null))
                    || ((left != null) && (right != null) && (mid != null));
            return (left == null) && (right == null) && (mid == null);
        }

        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    public static void compress() {
        // TODO: ADD YOUR CODE HERE
    }


    public static void expand() {
        // TODO: ADD YOUR CODE HERE
    }

}

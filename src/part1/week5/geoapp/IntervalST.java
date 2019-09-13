package part1.week5.geoapp;

public class IntervalST<Key extends Comparable<Key>, Value> implements IntervalSearchTree<Key, Value> {
    private Node root;

    private class Node {
        Key lo, hi;
        Value val;
        Node left, right;
        int N;
        Key max;

        public Node(Key lo, Key hi, Value val) {
            this.lo = lo;
            this.hi = hi;
            this.val = val;
            this.N = 1;
            this.max = hi;
        }

    }

    public IntervalST() {
    }

    public int size() {
        // TODO : ADD YOUR CODE HERE
        return 0;
    }

    public void put(Key lo, Key hi, Value val) {
        // TODO : ADD YOUR CODE HERE
    }


    public Value get(Key lo, Key hi) {
        // TODO : ADD YOUR CODE HERE
        return null;
    }


    public Value delete(Key lo, Key hi) {
        // TODO : ADD YOUR CODE HERE
        return null;
    }


    public Iterable<Value> intersects(Key lo, Key hi) {
        // TODO : ADD YOUR CODE HERE
        return null;
    }


    // check integrity of subtree count fields
    public boolean check() {
        return checkCount() && checkMax();
    }

    // check integrity of count fields
    private boolean checkCount() {
        // TODO : ADD YOUR CODE HERE
        return false;
    }


    private boolean checkMax() {
        // TODO : ADD YOUR CODE HERE
        return false;
    }


}

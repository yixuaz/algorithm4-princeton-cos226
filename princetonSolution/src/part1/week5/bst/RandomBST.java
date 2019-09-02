package part1.week5.bst;

import edu.princeton.cs.algs4.StdRandom;

public class RandomBST<Key extends Comparable<Key>, Val> extends BST<Key, Val> {
    // RANDOM INSERT

    // make new node the root with uniform probability
    @Override
    protected Node put(Node x, Key key, Val val) {
        if (x == null) return new Node(key, val);
        int cmp = key.compareTo(x.key);
        if (cmp == 0) { x.val = val; return x; }
        if (StdRandom.bernoulli(1.0 / (getCount(x) + 1.0))) return putRoot(x, key, val);
        if (cmp < 0) x.left  = put(x.left,  key, val);
        else         x.right = put(x.right, key, val);
        return fix(x);
    }


    private Node putRoot(Node x, Key key, Val val) {
        if (x == null) return new Node(key, val);
        int cmp = key.compareTo(x.key);
        if      (cmp == 0) { x.val = val; return x; }
        else if (cmp  < 0) { x.left  = putRoot(x.left,  key, val); x = rotR(x); }
        else               { x.right = putRoot(x.right, key, val); x = rotL(x); }
        return x;
    }
    // right rotate
    private Node rotR(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = fix(h);
        return fix(x);
    }
    // left rotate
    private Node rotL(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = fix(h);
        return fix(x);
    }

    // RANDOM DELETE
    @Override
    protected Node remove(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp == 0) x = joinLR(x.left, x.right);
        else if (cmp  < 0) x.left  = remove(x.left,  key);
        else               x.right = remove(x.right, key);
        return fix(x);
    }

    private Node fix(Node x) {
        if (x == null) return null;
        return updateCount(x);
    }

    private Node joinLR(Node a, Node b) {
        if (a == null) return b;
        if (b == null) return a;

        if (StdRandom.bernoulli((double) getCount(a) / (getCount(a) + getCount(b))))  {
            a.right = joinLR(a.right, b);
            return fix(a);
        }
        else {
            b.left = joinLR(a, b.left);
            return fix(b);
        }
    }

}

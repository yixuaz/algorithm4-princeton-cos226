package part1.week5.geoapp;

import java.util.ArrayList;
import java.util.List;

public class IntervalST<Key extends Comparable<Key>, Value> implements IntervalSearchTree<Key,Value>{
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

        private void update(Node x) {
            this.lo = x.lo;
            this.hi = x.hi;
            this.val = x.val;
            this.N = x.N;
            this.max = x.max;
        }

        private void updateMax() {
            max = hi;
            if (left != null && left.max.compareTo(max) > 0)
                max = left.max;
            if (right != null && right.max.compareTo(max) > 0)
                max = right.max;
        }

        private boolean intersect(Key thatLow, Key thatHigh) {
            if (hi.compareTo(thatLow) < 0 || lo.compareTo(thatHigh) > 0) return false;
            return true;
        }
    }
    public IntervalST() {
    }
    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) {
            return 0;
        }
        return 1 + Math.max(height(x.left), height(x.right));
    }
    public int size() {
        return size(root);
    }
    private int size(Node cur) {
        if (cur == null) return 0;
        return cur.N;
    }
    public void put(Key lo, Key hi, Value val) {
        root = put(root, lo, hi, val);
    }
    private Node put(Node cur, Key lo, Key hi, Value val) {
        if (cur == null) return new Node(lo, hi, val);
        int compareResult = cur.lo.compareTo(lo);
        if (compareResult == 0) {
            cur.val = val;
        } else if (compareResult < 0) {
            cur.right = put(cur.right, lo, hi, val);
        } else {
            cur.left = put(cur.left, lo, hi, val);
        }
        cur.updateMax();
        cur.N = 1 + size(cur.left) + size(cur.right);
        return cur;
    }

    public Value get(Key lo, Key hi) {
        return get(root, lo, hi);
    }

    private Value get(Node x, Key lo, Key hi) {
        if (x == null) return null;
        int cmp = lo.compareTo(x.lo);
        if (cmp == 0) {
            cmp = hi.compareTo(x.hi);
        }
        if (cmp < 0) return get(x.left, lo, hi);
        else if (cmp > 0) return get(x.right, lo, hi);
        else return x.val;
    }

    public Value delete(Key lo, Key hi) {
        Value value = get(lo, hi);
        if (value == null) return null;
        root = delete(root, lo, hi);
        return value;
    }
    private Node delete(Node x, Key lo, Key hi) {
        if (x == null) {
            return null;
        }
        int cmp = lo.compareTo(x.lo);
        if (cmp == 0) {
            cmp = hi.compareTo(x.hi);
        }
        if (cmp < 0) {
            x.left  = delete(x.left, lo, hi);
        } else if (cmp > 0) {
            x.right = delete(x.right, lo, hi);
        } else {
            if (x.right == null) return x.left;
            Node rightMin = min(x.right);
            x.update(rightMin);
            x.right = delete(x.right, rightMin.lo, rightMin.hi);
        }
        x.updateMax();
        x.N = 1 + size(x.left) + size(x.right);
        return x;
    }

    private Node min(Node cur) {
        if (cur == null) return null;
        return (cur.left == null) ? cur : min(cur.left);
    }

    public Iterable<Value> intersects(Key lo, Key hi) {
        List<Value> ret = new ArrayList<>();
        intersects(root, lo, hi, ret);
        assert check();
        return ret;
    }

    private boolean intersects(Node cur, Key lo, Key hi, List<Value> ret) {
        boolean found1 = false, found2 = false, found3 = false;
        if (cur == null) return false;
        if (cur.intersect(lo, hi)) {
            ret.add(cur.val);
            found1 = true;
        }
        if (cur.left != null && cur.left.max.compareTo(lo) >= 0) {
            found2 = intersects(cur.left, lo, hi, ret);
        }
        if (found2 || cur.left == null || cur.left.max.compareTo(lo) < 0) {
            found3 = intersects(cur.right, lo, hi, ret);
        }
        return found1 || found2 || found3;
    }

    // check integrity of subtree count fields
    public boolean check() { return checkCount() && checkMax(); }

    // check integrity of count fields
    private boolean checkCount() { return checkCount(root); }
    private boolean checkCount(Node x) {
        if (x == null) return true;
        return checkCount(x.left) && checkCount(x.right) && (x.N == 1 + size(x.left) + size(x.right));
    }

    private boolean checkMax() { return checkMax(root); }
    private boolean checkMax(Node x) {
        if (x == null) return true;
        return x.max == max3(x.max, max(x.left), max(x.right));
    }
    private Key max(Node cur) {
        if (cur == null) return null;
        return cur.max;
    }
    private Key max3(Key a, Key b, Key c) {
        Key res = a;
        if (b != null && b.compareTo(res) > 0) res = b;
        if (c != null && c.compareTo(res) > 0) res = c;
        return res;
    }


}

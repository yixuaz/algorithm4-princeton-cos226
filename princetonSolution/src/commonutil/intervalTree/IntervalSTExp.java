package commonutil.intervalTree;

/*
 * Copyright 2016 Dmitry Avtonomov.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import week5.geoapp.IntervalSearchTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * A generic Interval Search Tree. Use {@link Interval1D} as keys. If you want to store multiple
 * values associated with an interval, just store a List in the tree.
 *
 *
 * Implementation is an adaptation of IntervalST class (http://algs4.cs.princeton.edu/93intersection/IntervalST.java.html)
 * provided with Algorithms 4 book by Robert Sedgewick and Kevin Wayne.
 *
 * Unlike the original, it allows insertion of Intervals which have the same left ends and is fully
 * generic.
 *
 * @param <K> the type of interval ends used by this tree
 * @param <V> values to be associated with the intervals
 * @author Dmitry Avtonomov
 */
public class IntervalSTExp<K extends Comparable<K>, V> implements Iterable<IntervalSTExp.Node<K, V>>, IntervalSearchTree<K,V> {

    @SuppressWarnings("unchecked")
    private final K MIN_OBJ = (K) new Comparable<K>() {
        @Override
        public int compareTo(K o) {
            if (o == MIN_OBJ) {
                return 0;
            }
            return -1;
        }
    };

    /**
     * root of the BST
     */
    private Node<K, V> root;

    /**
     * Print out the current tree from left to right.
     */
    public void prettyPrint() {
        if (root == null) {
            System.out.println("Map is empty");
        }
        prettyPrint(root);
    }

    /**
     * Recursively prints the tree.
     *
     * @param n the node to start recursion at
     */
    private void prettyPrint(Node<K, V> n) {
        if (n.left != null) {
            prettyPrint(n.left);
        }
        System.out.println(n);
        if (n.right != null) {
            prettyPrint(n.right);
        }
    }

    @Override
    public Iterator<IntervalSTExp.Node<K, V>> iterator() {
        return new IntervalSTIterator();
    }

    /**
     * Check if the tree contains this exact interval. This method is pretty useless, it's better to
     * and compare it's return value to null. This way you won't need to
     * query the tree twice.
     */
    public boolean contains(K lo, K hi) {
        return (get(lo, hi) != null);
    }

    /*************************************************************************
     *  BST search
     *************************************************************************/

    /**
     * Get the value associated with the given key (Interval). Interval must match exactly.
     *
     * @return null if no such key
     */
    public V get(K lo, K hi) {
        return get(root, new Interval1D<>(lo, hi)).value;
    }


    @Override
    public V delete(K lo, K hi) {
        return remove(lo, hi);
    }

    @Override
    public Iterable<V> intersects(K lo, K hi) {
        return searchAll(lo, hi).stream().map(a -> a.value).collect(Collectors.toList());
    }

    /**
     * Get the value associated with the given key (Interval). Interval must match exactly.
     *
     * @return null if no such key
     */
    private Node<K, V> get(Node<K, V> x, Interval1D<K> interval) {
        if (x == null) {
            return null;
        }
        int cmp = interval.compareTo(x.interval);
        if (cmp < 0) {
            return get(x.left, interval);
        } else if (cmp > 0) {
            return get(x.right, interval);
        } else {
            return x;
        }
    }

    /**
     * Insert a new node in the tree. If the tree contained that interval already, the value is
     * replaced.
     */
    public void put(K lo, K hi, V value) {
        Interval1D<K> interval = new Interval1D<>(lo, hi);
        Node<K, V> origNode = get(root, new Interval1D<>(lo, hi));
        if (origNode != null) {
            //System.err.println("Dupe is being put into the map. Interval: " + interval +
            //        "\n\tOld: " + origNode.getValue() + " New: " + value);
            assert false;
            origNode.setValue(value);
        } else {
            root = randomizedInsert(root, interval, value);
        }
    }



    /*************************************************************************
     *  randomized insertion
     *************************************************************************/

    // make new node the root with uniform probability
    private Node<K, V> randomizedInsert(Node<K, V> x, Interval1D<K> interval, V value) {
        if (x == null) {
            return new Node<>(interval, value);
        }
        if (java.lang.Math.random() * size(x) < 1.0) {
            return rootInsert(x, interval, value);
        }
        int cmp = interval.compareTo(x.interval);
        if (cmp < 0) {
            x.left = randomizedInsert(x.left, interval, value);
        } else {
            x.right = randomizedInsert(x.right, interval, value);
        }
        fix(x);
        return x;
    }

    private Node<K, V> rootInsert(Node<K, V> x, Interval1D<K> interval, V value) {
        if (x == null) {
            return new Node<>(interval, value);
        }
        int cmp = interval.compareTo(x.interval);
        if (cmp < 0) {
            x.left = rootInsert(x.left, interval, value);
            x = rotR(x);
        } else {
            x.right = rootInsert(x.right, interval, value);
            x = rotL(x);
        }
        return x;
    }

    /*************************************************************************
     *  deletion
     *************************************************************************/
    private Node<K, V> joinLR(Node<K, V> a, Node<K, V> b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        if (java.lang.Math.random() * (size(a) + size(b)) < size(a)) {
            a.right = joinLR(a.right, b);
            fix(a);
            return a;
        } else {
            b.left = joinLR(a, b.left);
            fix(b);
            return b;
        }
    }

    /**
     * Remove and return value associated with given interval.
     *
     * @return null, if this interval does not exist in the tree
     */
    public V remove(K lo, K hi) {
        Interval1D<K> interval = new Interval1D<>(lo, hi);
        V node = get(lo ,hi);
        root = remove(root, interval);
        return node;
    }

    private Node<K, V> remove(Node<K, V> h, Interval1D<K> interval) {
        if (h == null) {
            return null;
        }
        int cmp = interval.compareTo(h.interval);
        if (cmp < 0) {
            h.left = remove(h.left, interval);
        } else if (cmp > 0) {
            h.right = remove(h.right, interval);
        } else {
            h = joinLR(h.left, h.right);
        }
        fix(h);
        return h;
    }

    /**
     * Return an interval in data structure that intersects the given interval; Return null if no such
     * interval exists. Running time is proportional to log N
     */
    public Interval1D<K> search(Interval1D<K> interval) {
        return search(root, interval);

    }

    /*************************************************************************
     *  Interval searching
     *************************************************************************/

    /**
     * Look in subtree rooted at {@code x}.
     *
     * @param x the root where to start search
     * @param interval the interval to search for
     * @return null, if
     */
    private Interval1D<K> search(Node<K, V> x, Interval1D<K> interval) {
        while (x != null) {
            if (interval.intersects(x.interval)) {
                return x.interval;
            } else if (x.left == null) {
                x = x.right;
            } else if (x.left.max.compareTo(interval.lo) < 0) {
                x = x.right;
            } else {
                x = x.left;
            }
        }
        return null;
    }

    /**
     * Return <b>ALL</b> intervals in data structure that intersect the given interval. Running time
     * is proportional to R log N, where R is the number of intersections.
     *
     *
     * @return all intersections. Single point intersections are also counted.
     */
    public List<Node<K, V>> searchAll(K lo, K hi) {
        LinkedList<Node<K, V>> list = new LinkedList<>();
        searchAll(root, new Interval1D<>(lo, hi), list);
        return list;
    }

    // look in subtree rooted at x
    private boolean searchAll(Node<K, V> x, Interval1D<K> interval, List<Node<K, V>> toAppend) {
        boolean found1 = false;
        boolean found2 = false;
        boolean found3 = false;
        if (x == null) {
            return false;
        }
        if (interval.intersects(x.interval)) {
            toAppend.add(x);
            found1 = true;
        }

        if (x.left != null && x.left.max.compareTo(interval.lo) >= 0) {
            found2 = searchAll(x.left, interval, toAppend);
        }
        if (found2 || x.left == null || x.left.max.compareTo(interval.lo) < 0) {
            found3 = searchAll(x.right, interval, toAppend);
        }
        return found1 || found2 || found3;
    }

    /**
     * Return number of nodes in tree.
     */
    public int size() {
        return size(root);
    }

    /*************************************************************************
     *  useful binary tree functions
     *************************************************************************/

    /**
     * Number of nodes in subtree rooted at {@code x}.
     *
     * @param x subtree root
     */
    private int size(Node<K, V> x) {
        if (x == null) {
            return 0;
        } else {
            return x.N;
        }
    }

    // height of tree (empty tree height = 0)
    public int height() {
        return height(root);
    }

    private int height(Node<K, V> x) {
        if (x == null) {
            return 0;
        }
        return 1 + java.lang.Math.max(height(x.left), height(x.right));
    }

    /*************************************************************************
     *  helper BST functions
     *************************************************************************/

    // fix auxiliary information (subtree count and max fields)
    private void fix(Node<K, V> x) {
        if (x == null) {
            return;
        }
        x.N = 1 + size(x.left) + size(x.right);
        x.max = max3(x.interval.hi, max(x.left), max(x.right));
    }

    private K max(Node<K, V> x) {
        if (x == null) {
            return MIN_OBJ;
        }
        return x.max;
    }

    private K max2(K a, K b) {
        // if (a == null || b == null) throw new RuntimeException("BOTH/SOME OBJECTS WERE NULL");
        int cmp = 0;
        try {
            if (!b.getClass().equals(a.getClass())) {
                if (a == MIN_OBJ) {
                    cmp = -1;
                } else {
                    cmp = +1;
                }
            } else {
                cmp = a.compareTo(b);
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        if (cmp <= 0) {
            return b;
        }
        return a;
    }

    // precondition: a is not null
    private K max3(K a, K b, K c) {
        return max2(a, max2(b, c));
    }

    // right rotate
    private Node<K, V> rotR(Node<K, V> h) {
        Node<K, V> x = h.left;
        h.left = x.right;
        x.right = h;
        fix(h);
        fix(x);
        return x;
    }

    // left rotate
    private Node<K, V> rotL(Node<K, V> h) {
        Node<K, V> x = h.right;
        h.right = x.left;
        x.left = h;
        fix(h);
        fix(x);
        return x;
    }

    /**
     * Check integrity of subtree count fields.
     */
    public boolean check() {
        return checkCount() && checkMax();
    }

    /*************************************************************************
     *  Debugging functions that test the integrity of the tree
     *************************************************************************/

    /**
     * Check integrity of count fields.
     */
    private boolean checkCount() {
        return checkCount(root);
    }

    private boolean checkCount(Node<K, V> x) {
        if (x == null) {
            return true;
        }
        return checkCount(x.left) && checkCount(x.right) && (x.N == 1 + size(x.left) + size(x.right));
    }

    private boolean checkMax() {
        return checkMax(root);
    }

    private boolean checkMax(Node<K, V> x) {
        if (x == null) {
            return true;
        }
        return x.max == max3(x.interval.hi, max(x.left), max(x.right));
    }

    /**
     * BST helper node data type. Everything in the tree is stored in these Nodes.
     */
    public static class Node<U extends Comparable<U>, W> {

        private final Interval1D<U> interval;    // key
        private W value;                   // associated data
        private Node<U, W> left, right;    // left and right subtrees
        private int N;                     // size of subtree rooted at this node
        private U max; // max endpoint in subtree rooted at this node

        Node(Interval1D<U> interval, W value) {
            this.interval = interval;
            this.value = value;
            this.N = 1;
            this.max = interval.hi;
        }

        public Interval1D<U> getInterval() {
            return interval;
        }

        public W getValue() {
            return value;
        }

        /**
         * Use with caution. Can be used to update the values stored in the tree. First use {@link
         *
         */
        public void setValue(W value) {
            this.value = value;
        }

        @Override
        public String toString() {
            String valStr = value.toString();
            if (valStr.length() > 64) {
                valStr = valStr.substring(0, 61) + "...";
            }
            return interval.toString() + " => " + valStr;
        }
    }

    private class IntervalSTIterator implements Iterator<IntervalSTExp.Node<K, V>> {

        private int cntNodesVisited = 0;
        private IntervalSTExp.Node<K, V> lastVisitedNode = null;
        private LinkedList<Node<K, V>> parentStack;
        private int size;

        public IntervalSTIterator() {
            parentStack = new LinkedList<>();
            size = IntervalSTExp.this.size();
        }

        @Override
        public boolean hasNext() {
            return cntNodesVisited < size;
        }

        @Override
        public Node<K, V> next() {
            if (root == null) {
                return null;
            }
            if (cntNodesVisited == 0) { // change to cntNodesVisited == 0
                lastVisitedNode = findLeftMost(root);
            } else {
                lastVisitedNode = findNext(lastVisitedNode);
            }
            if (lastVisitedNode != null) {
                // if the node is not null, means we have visited something
                // need to update the counter
                cntNodesVisited++;
            }
            return lastVisitedNode;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Removals from tree by iterator are not supported. "
                    + "Use IntervalST.remove(Interval1D<K>) for that.");
        }

        /**
         * Find the left-most node in the tree
         */
        private Node<K, V> findLeftMost(Node<K, V> n) {
            while (n.left != null) {
                parentStack.push(n);
                n = n.left;
            }
            return n;
        }

        /**
         * Find the next node to the right of this one, in-order
         */
        private Node<K, V> findNext(Node<K, V> n) {
            if (n == null) {
                return null;
            }
            if (n.right != null) {
                parentStack.push(n);
                return findLeftMost(n.right);
            }
            while (!parentStack.isEmpty()) {
                Node<K, V> parent = parentStack.pop();
                if (n == parent.left) {
                    // if we make a right turn going up, then this parent is the successor
                    return parent;
                }
                // if we make a left turn, then we need to continue going up
                // don't need to do anything
                n = parent;
            }
            return null;
        }
    }

}
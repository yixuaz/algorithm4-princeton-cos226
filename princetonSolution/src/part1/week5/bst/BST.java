package part1.week5.bst;

import java.util.NoSuchElementException;

public class BST<Key extends Comparable<Key>, Val> implements BinarySearchTree<Key, Val> {
    protected class Node {
        public Key key;
        public Val val;
        public Node left, right;
        int count;

        public Node(Key key, Val val) {
            this.key = key;
            this.val = val;
            count = 1;
        }
    }

    protected Node root;

    protected int getCount(Node cur) {
        if (cur == null) return 0;
        return cur.count;
    }

    protected Node updateCount(Node cur) {
        cur.count = 1 + getCount(cur.left) + getCount(cur.right);
        return cur;
    }

    public int size() {
        return getCount(root);
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean contains(Key key) {
        if (key == null)
            throw new IllegalArgumentException("key not null");
        Node cur = find(root, key);
        return cur != null;
    }

    public Val find(Key key) {
        if (key == null)
            throw new IllegalArgumentException("key not null");
        Node cur = find(root, key);
        if (cur == null) return null;
        return cur.val;
    }

    private Node find(Node cur, Key key) {
        if (cur == null) return null;
        int compareResult = cur.key.compareTo(key);
        if (compareResult == 0) return cur;
        return (compareResult < 0) ? find(cur.right, key) : find(cur.left, key);
    }

    public void put(Key key, Val val) {
        if (key == null)
            throw new IllegalArgumentException("key not null");
        root = put(root, key, val);
    }

    protected Node put(Node cur, Key key, Val val) {
        if (cur == null) return new Node(key, val);
        int compareResult = cur.key.compareTo(key);
        if (compareResult == 0) {
            cur.val = val;
        } else if (compareResult < 0) {
            cur.right = put(cur.right, key, val);
        } else {
            cur.left = put(cur.left, key, val);
        }
        return updateCount(cur);
    }

    public boolean remove(Key key) {
        if (key == null)
            throw new IllegalArgumentException("key not null");
        int oriSize = size();
        root = remove(root, key);
        return size() < oriSize;
    }

    protected Node remove(Node cur, Key key) {
        if (cur == null) return null;
        int compareResult = cur.key.compareTo(key);
        if (compareResult > 0) {
            cur.left = remove(cur.left, key);
        } else {
            if (compareResult == 0 && cur.right == null) {
                return cur.left;
            }
            if (compareResult == 0) {
                Node rightMin = findMin(cur.right);
                cur.key = rightMin.key;
                cur.val = rightMin.val;
                cur.right = remove(cur.right, rightMin.key);
            } else {
                cur.right = remove(cur.right, key);
            }
        }
        return updateCount(cur);
    }

    protected Node findMin(Node cur) {
        if (cur == null) return null;
        return cur.left == null ? cur : findMin(cur.left);
    }

    public Key select(int rank) {
        if (rank < 0 || rank >= size())
            throw new NoSuchElementException("rank is out of range");
        return select(root, rank).key;
    }

    protected Node select(Node cur, int rank) {
        if (getCount(cur.left) == rank) return cur;
        return getCount(cur.left) > rank ? select(cur.left, rank)
                : select(cur.right, rank - getCount(cur.left) - 1);
    }

    public int rank(Key key) {
        if (key == null)
            throw new IllegalArgumentException("key not null");
        return rank(root, key);
    }

    protected int rank(Node cur, Key key) {
        if (cur == null) return 0;
        int compareResult = cur.key.compareTo(key);
        if (compareResult > 0) return rank(cur.left, key);
        else if (compareResult == 0) return getCount(cur.left);
        return getCount(cur.left) + 1 + rank(cur.right, key);
    }

    public Key floor(Key key) {
        if (key == null)
            throw new IllegalArgumentException("key not null");
        Node tar = floor(root, key);
        return tar == null ? null : tar.key;
    }

    protected Node floor(Node cur, Key key) {
        if (cur == null) return null;
        int comparResult = cur.key.compareTo(key);
        if (comparResult == 0) return cur;
        if (comparResult < 0) {
            Node tar = floor(cur.right, key);
            return tar == null ? cur : tar;
        }
        return floor(cur.left, key);
    }

    public Key ceil(Key key) {
        if (key == null)
            throw new IllegalArgumentException("key not null");
        Node tar = ceil(root, key);
        return tar == null ? null : tar.key;
    }

    protected Node ceil(Node cur, Key key) {
        if (cur == null) return null;
        int comparResult = cur.key.compareTo(key);
        if (comparResult == 0) return cur;
        if (comparResult > 0) {
            Node tar = ceil(cur.left, key);
            return tar == null ? cur : tar;
        }
        return ceil(cur.right, key);
    }

}

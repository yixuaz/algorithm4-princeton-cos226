package week5.bst;

public class RedBlackBSTWithNoExtraMemory<Key extends Comparable<Key>, Val> extends BST<Key, Val> {

    private boolean isRed(Node cur) {
        // TODO : ADD YOUR CODE HERE
        return false;
    }

    private void flipColors(Node cur) {
        // TODO : ADD YOUR CODE HERE
    }

    private Node setBlack(Node x) {
        // TODO : ADD YOUR CODE HERE
        return x;
    }
    private Node setRed(Node x) {
        // TODO : ADD YOUR CODE HERE
        return x;
    }

    private Node rotateLeft(Node x) {
        // TODO : ADD YOUR CODE HERE
        return x;
    }

    private Node rotateRight(Node x) {
        // TODO : ADD YOUR CODE HERE
        return x;
    }

    private Node balance(Node x) {
        if (isRed(x.right)) x = rotateLeft(x);
        if (isRed(x.left) && isRed(x.left.left)) x = rotateRight(x);
        if (isRed(x.right) && isRed(x.left)) flipColors(x);
        return x;
    }
    @Override
    protected Node updateCount(Node cur) {
        cur = balance(cur);
        boolean isBlack = !isRed(cur);
        super.updateCount(cur);
        if (isBlack) setBlack(cur);
        return cur;
    }

    // below code is for remove
    private Node moveRedLeft(Node x) {
        flipColors(x);
        // we don't allow two sequential red, so check below
        if (isRed(x.right.left)) {
            x.right = rotateRight(x.right);
            x = rotateLeft(x);
            flipColors(x);
        }
        return x;
    }

    private Node moveRedRight(Node x) {
        flipColors(x);
        // we don't allow two sequential red, so check below, x.left is red by above action
        if (isRed(x.left.left)) {
            x = rotateRight(x);
            flipColors(x);
        }
        return x;
    }

    @Override
    public boolean remove(Key key) {
        if (key == null)
            throw new IllegalArgumentException("key not null");
        if (!contains(key)) return false;
        if (!isRed(root.left) && !isRed(root.right))
            setRed(root);
        boolean res = super.remove(key);
        if (!isEmpty()) setBlack(root);
        return true;
    }
    @Override
    protected Node remove(Node cur, Key key) {
        if (cur == null) return null;
        if (cur.key.compareTo(key) > 0) {
            // invariant: recursion to left, make sure left is red or left left is red
            if (!isRed(cur.left) && !isRed(cur.left.left))
                cur = moveRedLeft(cur);
            cur.left = remove(cur.left, key);
        } else {
            // invariant: recursion to right, make sure right is red or right left is red
            if (isRed(cur.left)) // because red left lean, so make it to right
                cur = rotateRight(cur);
            if (cur.key.compareTo(key) == 0 && cur.right == null) {
                return cur.left;
            }
            if (!isRed(cur.right) && !isRed(cur.right.left))
                cur = moveRedRight(cur);
            //cur node may changed , so re calculate compareResult
            if (cur.key.compareTo(key) == 0) {
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



}

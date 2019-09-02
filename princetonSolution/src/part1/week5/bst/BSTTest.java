package part1.week5.bst;

import edu.princeton.cs.algs4.RedBlackBST;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

public class BSTTest {
    protected static final Random r = new Random();
    protected static final int N = 100000;

    protected RedBlackBST<Integer,Integer> expect;
    protected BinarySearchTree<Integer, Integer> bst;

    protected BinarySearchTree<Integer, Integer> getToBeTestedBst() {
        return new BST<>();
    }
    @Before
    public void setup() {
        bst = getToBeTestedBst();
        expect = new RedBlackBST<>();
        for (int i = 0; i < N; i++) {
            int val = -N + r.nextInt(2 * N);
            expect.put(val, val);
            bst.put(val, val);
        }
    }

    @Test
    public void contains() {
        for (int i = 0; i < N; i++) {
            Assert.assertEquals(expect.contains(i), bst.contains(i));
        }
    }
    @Test
    public void size() {
        Assert.assertEquals(expect.size(), bst.size());
    }

    @Test
    public void isEmpty() {
        Assert.assertEquals(expect.isEmpty(), bst.isEmpty());
    }

    @Test
    public void find() {
        for (int i = 0; i < N; i++) {
            Assert.assertEquals(expect.get(i), bst.find(i));
        }
    }

    @Test
    public void putAndremove() {
        for (int i = 0; i < N; i++) {
            int val = -N + r.nextInt(2 * N);
            if (r.nextBoolean()) {
                expect.delete(val);
                bst.remove(val);
            } else {
                expect.put(val, val);
                bst.put(val, val);
            }
            Assert.assertEquals(expect.get(val), bst.find(val));
        }
    }

    @Test
    public void select() {
        for (int i = 0; i < expect.size(); i++) {
            Assert.assertEquals(expect.select(i), bst.select(i));
        }
    }

    @Test
    public void rank() {
        for (int i = 0; i < N; i++) {
            Assert.assertEquals(expect.rank(i), bst.rank(i));
        }
    }

    @Test
    public void floor() {
        for (int i = 0; i < N; i++) {
            Assert.assertEquals(expect.floor(i), bst.floor(i));
        }
    }

    @Test
    public void ceil() {
        for (int i = 0; i < N; i++) {
            Assert.assertEquals(expect.ceiling(i), bst.ceil(i));
        }
    }
}
package part1.week5.bst;

import edu.princeton.cs.algs4.RedBlackBST;
import org.junit.Test;

import static org.junit.Assert.*;

public class RedBlackBSTWithNoExtraMemoryTest extends BSTTest{

    @Override
    protected BinarySearchTree<Integer, Integer> getToBeTestedBst() {
        return new RedBlackBSTWithNoExtraMemory<>();
    }

    @Test(timeout = 500)
    public void testSequentialInsetAndRemove() {
        RedBlackBST<Integer, Integer> expect = new RedBlackBST<>();
        BinarySearchTree<Integer, Integer> bst = getToBeTestedBst();
        for (int i = 0; i < N; i++) {
            expect.put(i,i);
            bst.put(i,i);
        }
        for (int i = 0; i < N; i++) {
            assertEquals(expect.get(i),bst.find(i));
        }

        for (int i = N - 1; i >= N/2; i--) {
            expect.delete(i);
            bst.remove(i);
            assertEquals(expect.get(i),bst.find(i));
        }

        for (int i = N/2 - 1; i >= 0; i--) {
            assertEquals(expect.get(i),bst.find(i));
        }

        for (int i = 0; i < N/2; i++) {
            expect.delete(i);
            bst.remove(i);
            assertEquals(expect.get(i),bst.find(i));
        }
        assertTrue(bst.isEmpty());
    }
}
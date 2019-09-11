package part1.week5.bst;

import edu.princeton.cs.algs4.RedBlackBST;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RedBlackBSTWithNoExtraMemoryTest extends BSTTest {

    @Override
    protected BST<Integer, Integer> getToBeTestedBst() {
        return new RedBlackBSTWithNoExtraMemory<>();
    }

    @Test
    public void testSequentialInsetAndRemove() {
        RedBlackBST<Integer, Integer> expect = new RedBlackBST<>();
        BST<Integer, Integer> bst = new RedBlackBSTWithNoExtraMemory<>();
        for (int i = 0; i < N; i++) {
            expect.put(i, i);
            bst.put(i, i);
        }
        for (int i = 0; i < N; i++) {
            assertEquals(expect.get(i), bst.find(i));
        }

        for (int i = N - 1; i >= N / 2; i--) {
            expect.delete(i);
            bst.remove(i);
            assertEquals(expect.get(i), bst.find(i));
        }

        for (int i = N / 2 - 1; i >= 0; i--) {
            assertEquals(expect.get(i), bst.find(i));
        }

        for (int i = 0; i < N / 2; i++) {
            expect.delete(i);
            bst.remove(i);
            assertEquals(expect.get(i), bst.find(i));
        }
        assertTrue(bst.isEmpty());
    }
}
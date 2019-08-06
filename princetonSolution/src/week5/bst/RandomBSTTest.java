package week5.bst;

import static org.junit.Assert.*;

public class RandomBSTTest extends RedBlackBSTWithNoExtraMemoryTest{

    @Override
    protected BinarySearchTree<Integer, Integer> getToBeTestedBst() {
        return new RandomBST<>();
    }
}
package part1.week5.bst;

public class RandomBSTTest extends RedBlackBSTWithNoExtraMemoryTest {

    @Override
    protected BinarySearchTree<Integer, Integer> getToBeTestedBst() {
        return new RandomBST<>();
    }
}
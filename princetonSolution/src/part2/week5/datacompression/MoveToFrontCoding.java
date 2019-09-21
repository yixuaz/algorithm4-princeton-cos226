package part2.week5.datacompression;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.RedBlackBST;

/**
 * this problem could be solved by redblack tree, rank and select function.
 * rank function is to give a key, return the rank of the key.
 * select function is to give a rank, return the key which is on this rank.
 *
 * if we want to move to front, we can remove it from red black tree, and mark a (min - 1) key, to add this node back.
 * therefore, we need maintain a map for key(is 'a'-'z'), value is (key in red black tree)
 */
public class MoveToFrontCoding {
    private static final int R = 256;

    public static void encode() {
        RedBlackBST<Integer, Character> rbt = new RedBlackBST<>();
        int[] map = new int[R];
        for (char i = 0; i < R; i++) {
            rbt.put((int) i, i);
            map[i] = i;
        }
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int key = map[c];
            BinaryStdOut.write((char) rbt.rank(key));
            rbt.delete(key);
            map[c] = rbt.min() - 1;
            rbt.put(map[c], c);
        }
        BinaryStdOut.close();
    }

    public static void decode() {
        RedBlackBST<Integer, Character> rbt = new RedBlackBST<>();
        for (char i = 0; i < R; i++) {
            rbt.put((int) i, i);
        }
        while (!BinaryStdIn.isEmpty()) {
            int i = BinaryStdIn.readChar();
            Integer key = rbt.select(i);
            char c = rbt.get(key);
            BinaryStdOut.write(c);
            rbt.delete(key);
            rbt.put(rbt.min() - 1, c);
        }
        BinaryStdOut.close();
    }

}

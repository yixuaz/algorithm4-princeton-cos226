package part2.week5.datacompression;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.Huffman;

import java.util.PriorityQueue;

public class TernaryHuffmanCodes {
    private static final int R = 256;

    private TernaryHuffmanCodes() { }

    private static class Node implements Comparable<Node> {
        private final char ch;
        private final int freq;
        private final Node left, mid, right;

        Node(char ch, int freq, Node left, Node mid, Node right) {
            this.ch    = ch;
            this.freq  = freq;
            this.left  = left;
            this.mid = mid;
            this.right = right;
        }

        private boolean isLeaf() {
            assert ((left == null) && (right == null) && (mid == null))
                    || ((left != null) && (right != null) && (mid != null));
            return (left == null) && (right == null) && (mid == null);
        }

        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    public static void compress() {
        // step 1. read input
        String s = BinaryStdIn.readString();
        char[] cs = s.toCharArray();

        // step 2. build freq table
        int[] freq = new int[R];
        for (char c : cs) freq[c]++;

        // step 3. build trie
        Node root = buildTrie(freq);

        // step 4. build code table
        String[] st = new String[R];
        buildCode(st, root, new StringBuilder());

        // step 5. print trie for decode
        writeTrie(root);
        BinaryStdOut.write(cs.length);

        // step 6. encode input
        for (char c : cs) {
            for (char j : st[c].toCharArray()) {
                if (j == '0') {
                    BinaryStdOut.write(0,2);
                } else if (j == '1') {
                    BinaryStdOut.write(1, 2);
                } else if (j == '2') {
                    BinaryStdOut.write(2, 2);
                } else {
                    throw new IllegalArgumentException("invalid area");
                }

            }
        }
        // close output stream
        BinaryStdOut.close();
    }

    private static void writeTrie(Node x) {
        if (x.isLeaf()) {
            BinaryStdOut.write(true);
            BinaryStdOut.write(x.ch, 8);
            return;
        }
        BinaryStdOut.write(false);
        writeTrie(x.left);
        writeTrie(x.mid);
        writeTrie(x.right);
    }

    private static void buildCode(String[] st, Node x, StringBuilder sb) {
        if (!x.isLeaf()) {
            int len = sb.length();
            sb.append('0');
            buildCode(st, x.left, sb);
            sb.setCharAt(len,'1');
            buildCode(st, x.mid, sb);
            sb.setCharAt(len,'2');
            buildCode(st, x.right, sb);
            sb.deleteCharAt(len);
        } else {
            st[x.ch] = sb.toString();
        }
    }

    private static Node buildTrie(int[] freq) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        //int validPos = -1;
        for (int i = 0; i < R; i++) {
            if (freq[i] > 0) {
                pq.offer(new Node((char) i, freq[i],null, null, null));
            } /*else {
                validPos = i;
            }*/
        }

        while (pq.size() < 3 || pq.size() % 2 == 0) {
//            if (validPos == -1) {
//                throw new IllegalArgumentException("fail to Compress Because no valid position for freq[char]");
//            }
            pq.offer(new Node('\0', 0, null, null,null)); // why need valid pos here

        }
        while (pq.size() > 1) {
            Node left  = pq.poll();
            Node mid = pq.poll();
            Node right = pq.poll();
            Node parent = new Node('\0', left.freq + mid.freq + right.freq, left, mid, right);
            pq.offer(parent);
        }
        return pq.poll();
    }

    public static void expand() {
        // step 1. read trie
        Node root = readTrie();
        // step 2. read length
        int length = BinaryStdIn.readInt();
        // step 3. decode input
        for (int i = 0; i < length; i++) {
            Node x = root;
            while (!x.isLeaf()) {
                int bit = BinaryStdIn.readInt(2);
                if (bit == 0) x = x.left;
                else if (bit == 1) x = x.mid;
                else x = x.right;
            }
            BinaryStdOut.write(x.ch, 8);
        }
        BinaryStdOut.close();
    }

    private static Node readTrie() {
        boolean isLeaf = BinaryStdIn.readBoolean();
        if (isLeaf) {
            return new Node(BinaryStdIn.readChar(), -1, null, null, null);
        }
        return new Node('\0', -1, readTrie(), readTrie(), readTrie());
    }
}

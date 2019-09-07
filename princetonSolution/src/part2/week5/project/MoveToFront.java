package part2.week5.project;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static final int R = 256;
    private static final char[] ASCII = new char[R];

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        for (char i = 0; i < R; i++)
            ASCII[i] = i;
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int i = 0;
            for (; i < R; i++) {
                if (c == ASCII[i]) break;
            }
            BinaryStdOut.write((char) i);
            System.arraycopy(ASCII, 0, ASCII, 1, i);
            ASCII[0] = c;
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        for (char i = 0; i < R; i++)
            ASCII[i] = i;
        while (!BinaryStdIn.isEmpty()) {
            int i = BinaryStdIn.readChar();
            char c = ASCII[i];
            BinaryStdOut.write(c);
            System.arraycopy(ASCII, 0, ASCII, 1, i);
            ASCII[0] = c;
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
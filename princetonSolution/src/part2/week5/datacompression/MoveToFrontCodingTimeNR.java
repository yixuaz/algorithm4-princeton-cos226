package part2.week5.datacompression;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class MoveToFrontCodingTimeNR {
    private static final int R = 256;
    private static final char[] ASCII = new char[R];
    public static void encode() {
        for (char i = 0; i < R; i++)
            ASCII[i] = i;
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            char i = 0;
            for (; i < R; i++) {
                if (c == ASCII[i]) break;
            }
            BinaryStdOut.write(i);
            System.arraycopy(ASCII, 0, ASCII, 1, i);
            ASCII[0] = c;
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        for (char i = 0; i < R; i++)
            ASCII[i] = i;
        StringBuilder stringBuilder = new StringBuilder();
        while (!BinaryStdIn.isEmpty()) {
            int i = BinaryStdIn.readChar();
            char c = ASCII[i];
            BinaryStdOut.write(c);
            System.arraycopy(ASCII, 0, ASCII, 1, i);
            ASCII[0] = c;
        }
        BinaryStdOut.close();
    }
}

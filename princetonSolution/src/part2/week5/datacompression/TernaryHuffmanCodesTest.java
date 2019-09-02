package part2.week5.datacompression;

import commonutil.RandomStringBuilder;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.Huffman;
import edu.princeton.cs.algs4.StdIn;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class TernaryHuffmanCodesTest {
    private static PrintStream stdout = System.out;

    @Test
    public void basicTest() throws IOException {
        String input = "\0abcdefgabcdefgabcdefgabcdefgabcdefgabcdefgabcdefgabcdefg";
        testTemplate(true, input);
        input = "\0abcdefgabcdefgabcdefgabcdefgabcdefgabcdefgabcdefgabcdefg\0";
        testTemplate(true, input);
        input = "\0";
        testTemplate(true, input);
        input = "\0\0";
        testTemplate(true, input);
        input = "\0\0\0";
        testTemplate(true, input);
    }

    @Test
    public void randomTest256() throws IOException {
        int N = 10;
        for (int i = 0; i < 1000; i++) {
            if (i % 100 == 99) N <<= 1;
            String input = RandomStringBuilder.randomStringExtendASCII256(N);
            testTemplate(true, input);
        }
    }

    @Test
    public void randomTestLowerCase() throws IOException {
        int N = 10;
        for (int i = 0; i < 1000; i++) {
            if (i % 100 == 99) N <<= 1;
            String input = RandomStringBuilder.randomStringLowerCase(N);
            testTemplate(true, input);
        }
    }
    @Test
    public void randomTestBase62() throws IOException {
        int N = 10;
        for (int i = 0; i < 1000; i++) {
            if (i % 100 == 99) N <<= 1;
            String input = RandomStringBuilder.randomStringBase62(N);
            testTemplate(true, input);
            testTemplate(false, input);
        }
    }
    @Test
    public void randomTest012() throws IOException {
        int N = 10;
        for (int i = 0; i < 1000; i++) {
            if (i % 100 == 99) N <<= 1;
            String input = RandomStringBuilder.randomString(N,"012");
            testTemplate(true, input);
            testTemplate(false, input);
        }
    }

    private void testTemplate(boolean isTernary, String input) throws IOException {

        ByteArrayOutputStream compressResult = prepareCompressTestEnv(input.getBytes("UTF-8"));

        if (isTernary) TernaryHuffmanCodes.compress();
        else Huffman.compress();

        System.setOut(stdout);
        int compressLen = compressResult.toByteArray().length, originLength = input.getBytes().length;
        System.out.println((isTernary ? "Ternary Huffman" : "Binary Huffman") + " compress ratio : " + compressLen + "/" + originLength);

        ByteArrayOutputStream originText = prepareExpandTestEnv(compressResult.toByteArray());
        if (isTernary) TernaryHuffmanCodes.expand();
        else Huffman.expand();

        assertEquals(input, new String(originText.toByteArray(), StandardCharsets.UTF_8));
        BinaryStdIn.close();
    }

    private ByteArrayOutputStream prepareCompressTestEnv(byte[] input) throws IOException {
        System.setIn(new ByteArrayInputStream(input));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream outputStream = new PrintStream(baos, true, "UTF-8");
        System.setOut(outputStream);
        return baos;
    }

    private ByteArrayOutputStream prepareExpandTestEnv(byte[] compressResult) throws IOException {
        BinaryStdIn.close();
        System.setIn(new ByteArrayInputStream(compressResult));
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream outputStream = new PrintStream(baos, true, "UTF-8");
        System.setOut(outputStream);
        return baos;
    }

}
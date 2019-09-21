package part2.week5.datacompression;


import commonutil.RandomStringBuilder;
import edu.princeton.cs.algs4.BinaryStdIn;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertArrayEquals;

public class MoveToFrontCodingTest {
    @Test
    public void basicTest() throws UnsupportedEncodingException {
        testTemplate("abcdefgabcdefgabcdefgabcdefgabcdefg");
        testTemplate("\0\1\0");
        testTemplate("abcabcabcabc");
        testTemplate("****************");
    }

    @Test
    public void randomTest() throws UnsupportedEncodingException {
        for (int i = 100; i < 409600; i <<= 1) {
            String input = RandomStringBuilder.randomString(i, "&");
            testTemplate(input);

            input = RandomStringBuilder.randomStringLowerCase(i);
            testTemplate(input);

            input = RandomStringBuilder.randomStringExtendASCII256(i);
            testTemplate(input);
            System.out.println("pass " + i);
        }
    }

    private void testTemplate(String input) throws UnsupportedEncodingException {
        ByteArrayOutputStream expect = prepareTestEnv(input);
        MoveToFrontCodingTimeNR.encode();
        BinaryStdIn.close();

        ByteArrayOutputStream test = prepareTestEnv(input);
        MoveToFrontCoding.encode();
        BinaryStdIn.close();


        assertArrayEquals(expect.toByteArray(), test.toByteArray());

        ByteArrayOutputStream origin = prepareTestEnv(test.toString(StandardCharsets.US_ASCII.name()));
        MoveToFrontCoding.decode();
        BinaryStdIn.close();

        assertArrayEquals(input.getBytes(StandardCharsets.US_ASCII), origin.toByteArray());
    }

    private ByteArrayOutputStream prepareTestEnv(String input) throws UnsupportedEncodingException {
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.US_ASCII)));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream outputStream = new PrintStream(baos, true, StandardCharsets.US_ASCII.name());
        System.setOut(outputStream);
        return baos;
    }

}
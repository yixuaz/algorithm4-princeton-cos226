package part2.week4.substringsearch;

import commonutil.RandomStringBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;


public class LongestPalindromicSubStrTest {
    @Test
    public void basicTest() {
        String test = "cabad";
        Assert.assertEquals("aba", LongestPalindromicSubStr.findTimeN(test));
        Assert.assertEquals("aba", LongestPalindromicSubStr.findTimeNlgN(test));
        test = "cabac";
        Assert.assertEquals("cabac", LongestPalindromicSubStr.findTimeN(test));
        Assert.assertEquals("cabac", LongestPalindromicSubStr.findTimeNlgN(test));
        test = "";
        Assert.assertEquals("", LongestPalindromicSubStr.findTimeN(test));
        Assert.assertEquals("", LongestPalindromicSubStr.findTimeNlgN(test));
        test = "abc";
        Assert.assertEquals("a", LongestPalindromicSubStr.findTimeN(test));
        Assert.assertEquals("a", LongestPalindromicSubStr.findTimeNlgN(test));
        test = "abb";
        Assert.assertEquals("bb", LongestPalindromicSubStr.findTimeN(test));
        Assert.assertEquals("bb", LongestPalindromicSubStr.findTimeNlgN(test));
        test = "abbddb";
        Assert.assertEquals("bddb", LongestPalindromicSubStr.findTimeN(test));
        Assert.assertEquals("bddb", LongestPalindromicSubStr.findTimeNlgN(test));
    }

    @Test
    public void randomTest() {
        for (int i = 100; i < 1000; i++) {
            String input = RandomStringBuilder.randomString(i, "abcd");
            String expect = LongestPalindromicSubStr.findTimeN(input);
            Assert.assertEquals(expect, LongestPalindromicSubStr.findTimeNlgN(input));
            System.out.println(i + expect);
        }
    }

    @Test(timeout = 2500)
    public void performanceTest() {
        for (int i = 1000; i < 8000000; i <<= 1) {
            char[] cs = new char[i];
            Arrays.fill(cs, 'a');
            String input = new String(cs);
            long st = System.currentTimeMillis();
            LongestPalindromicSubStr.findTimeN(input);
            System.out.println("time 1: " + (System.currentTimeMillis() - st));
            st = System.currentTimeMillis();
            LongestPalindromicSubStr.findTimeNlgN(input);
            System.out.println("time 2: " + (System.currentTimeMillis() - st));
//            st = System.currentTimeMillis();
//            LongestPalindromicSubStr.findTimeN2(input);
//            System.out.println("time 3: " + (System.currentTimeMillis() - st));
        }
    }

}
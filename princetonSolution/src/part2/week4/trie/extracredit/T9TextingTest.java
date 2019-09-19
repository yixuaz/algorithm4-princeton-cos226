package part2.week4.trie.extracredit;

import commonutil.RandomStringBuilder;
import commonutil.Shuffler;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SuffixArray;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class T9TextingTest {

    private final int[] map = {2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 9, 9, 9, 9};

    @Test
    public void randomTest() throws IOException {
        Set<String> dict = new HashSet<>();
        FileReader fileReader = new FileReader(new File("src\\part2\\week4\\" +
                "trie\\extracredit\\dictionary-twl06.txt"));
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while (true) {
            String word =  bufferedReader.readLine();
            if (word == null) break;
            if (word.length() > 0)
                dict.add(word.toLowerCase());
        }
        T9Texting t9Texting = new T9Texting(new ArrayList<>(dict));
        int N = 2;
        for (int i = 0; i < 1000; i++) {
            if (i % 100 == 99) N++;
            String input = RandomStringBuilder.randomString(N, "23456789");
            List<String> testResult = t9Texting.query(input);
            if (testResult.isEmpty()) continue;
            System.out.println("input : " + input);
            System.out.println("query result :");
            for (String word : testResult) {
                System.out.println(word);
                StringBuilder sb = new StringBuilder();
                for (char c : word.toCharArray()) {
                    sb.append(map[c - 'a']);
                }
                Assert.assertEquals(input, sb.toString());
            }

        }
    }

}
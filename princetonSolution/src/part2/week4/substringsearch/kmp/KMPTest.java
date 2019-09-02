package part2.week4.substringsearch.kmp;

import commonutil.RandomStringBuilder;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class KMPTest {
    @Test
    public void randomTest() {
        int N = 300;
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            if (i % 100 == 9) N <<= 1;
            String text = RandomStringBuilder.randomStringBase62(N);

            if (r.nextBoolean()) {
                int st = r.nextInt(N / 2);
                String pattern = text.substring(st, st + 10);
                KMP dfa = new KMP(pattern);
                KMPplus nfa = new KMPplus(pattern);
                assertTrue(dfa.search(text) != -1);
                assertTrue(nfa.search(text) != -1);
            } else {
                String pattern = RandomStringBuilder.randomStringLowerCase(1 + r.nextInt(10));
                KMP dfa = new KMP(pattern);
                KMPplus nfa = new KMPplus(pattern);
                System.out.println(expect(text,pattern));
                assertEquals(expect(text,pattern), dfa.search(text));
                assertEquals(dfa.search(text), nfa.search(text));
            }
        }
    }


    private int expect(String text, String pattern) {
        return text.indexOf(pattern);
    }

}
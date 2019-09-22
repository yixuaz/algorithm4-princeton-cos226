package part2.week4.substringsearch.extracredit;

import commonutil.RandomStringBuilder;
import org.junit.Test;

import java.util.Random;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class BoyerMooreTest {
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
                assertTrue(BoyerMoore.search(text, pattern) != -1);
            } else {
                String pattern = RandomStringBuilder.randomStringLowerCase(1 + r.nextInt(10));
                System.out.println(expect(text,pattern));
                assertEquals(expect(text,pattern), BoyerMoore.search(text, pattern));
            }
        }
    }


    private int expect(String text, String pattern) {
        return text.indexOf(pattern);
    }
}
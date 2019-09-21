package part2.week5.datacompression.lzw;


import commonutil.RandomStringBuilder;
import org.junit.Assert;
import org.junit.Test;

public class MyLZWTest {
    @Test
    public void correctnessTest() {
        MyLZW lzw = new MyLZW(26, 'A');
        Assert.assertEquals("ABRACADABRABRABRA", lzw.expand(lzw.compress("ABRACADABRABRABRA")));
        Assert.assertEquals("ABABABA", lzw.expand(lzw.compress("ABABABA")));
    }
    @Test
    public void correctnessTest2() {
        MyLZW lzw = new MyLZW(256, 0);
        Assert.assertEquals("ABRACADABRABRABRA", lzw.expand(lzw.compress("ABRACADABRABRABRA")));
        Assert.assertEquals("ABABABA", lzw.expand(lzw.compress("ABABABA")));
    }

    @Test
    public void correctnessTest3() {
        MyLZW lzw = new MyLZW(4, 'A');
        int N = 10;
        for (int i = 0; i < 700; i++) {
            String random = RandomStringBuilder.randomString(N, "ABCD");
            byte[] compress = lzw.compress(random);
            double ratio = (double) compress.length / random.getBytes().length;
            System.out.println(ratio);
            Assert.assertTrue(ratio <= 1);
            Assert.assertEquals(random, lzw.expand(compress));
            if (i % 100 == 99) N <<= 1;
        }

    }

}
package part1.week2.stackqueue;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;
import java.util.Stack;
import java.util.TreeMap;

public class StackWithMaxTest {
    @Test
    public void RandomTest() {
        TreeMap<Integer, Integer> tmap = new TreeMap<>();
        StackWithMax<Integer> st = new StackWithMax<>();
        Stack<Integer> expect = new Stack<>();
        int N = 100000;
        Random r = new Random();
        for (int j = 1; j < 50; j += 5) {
            int base = 1 + (N / j * j * j);
            for (int i = 0; i < N; i++) {
                int val = r.nextInt(base);
                st.push(val);
                expect.push(val);
                tmap.put(val, tmap.getOrDefault(val, 0) + 1);
                Assert.assertEquals(expect.peek(), st.peek());
                Assert.assertEquals(expect.size(), st.size());
                Assert.assertEquals(tmap.lastKey(), st.max());
            }
            for (int i = 0; i < N; i++) {
                if (r.nextInt(2) < 1) {
                    int val = r.nextInt(base);
                    st.push(val);
                    expect.push(val);
                    tmap.put(val, tmap.getOrDefault(val, 0) + 1);
                    Assert.assertEquals(expect.peek(), st.peek());
                    Assert.assertEquals(tmap.lastKey(), st.max());
                } else {
                    int val = expect.peek();
                    Assert.assertEquals(expect.pop(), st.pop());
                    tmap.compute(val, (k, v) -> {
                        if (v == 1) return null;
                        return v - 1;
                    });
                    Assert.assertEquals(tmap.lastKey(), st.max());
                }
                Assert.assertEquals(expect.size(), st.size());
            }
            while (!expect.isEmpty()) {
                int val = expect.peek();
                Assert.assertEquals(tmap.lastKey(), st.max());
                Assert.assertEquals(expect.pop(), st.pop());
                tmap.compute(val, (k, v) -> {
                    if (v == 1) return null;
                    return v - 1;
                });

            }
        }

    }

}
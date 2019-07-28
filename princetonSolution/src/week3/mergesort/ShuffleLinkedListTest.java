package week3.mergesort;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import week3.mergesort.ShuffleLinkedList.Node;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

public class ShuffleLinkedListTest {
    private ShuffleLinkedList linkedList;
    @Before
    public void setup() {
        linkedList = new ShuffleLinkedList();
    }
    @Test
    public void testBuildLinkedList() {
        for (int i = 1; i < 20; i++) {
            Node cur = linkedList.buildLinkedList(i);
            Node p = cur;
            for (int j = 0; j < i; j++) {
                Assert.assertEquals(p.val,j);
                p = p.next;
            }
            Assert.assertEquals(null,p);
        }
    }

    @Test(timeout = 3000)
    public void testShuffle() {
        Random r = new Random();
        for (int n = 2; n <= 20; n ++) {
            System.out.println("test "+n);
            int N = 100000;
            int[][] check = new int[n][n];
            for (int i = 0; i < N; i++) {
                Node cur = linkedList.buildLinkedList(n);
                cur = linkedList.shuffle(cur);
                int[] res = new int[n];
                for (int j = 0; j < n; j++, cur = cur.next) {
                    res[cur.val]++;
                    check[j][cur.val]++;
                }
                int[] expect = new int[n];
                Arrays.fill(expect, 1);
                Assert.assertArrayEquals(expect, res);
            }
            double expect = N / n;
            for (int i = 0; i < n; i++) {
                // System.out.println(Arrays.toString(check[i]));
                for (int j = 0; j < n; j++) {

                    Assert.assertTrue(Math.abs(expect - check[i][j]) <= 0.06 * expect);
                }
            }
        }

    }

}
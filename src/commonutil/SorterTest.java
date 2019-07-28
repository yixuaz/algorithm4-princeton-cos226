package commonutil;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public abstract class SorterTest {
    public abstract ISorter getSorter();
    
    @Test
    public void testLotDuplicate() {
        Random r = new Random();
        int N = 10000;
        int[] arr = new int[N], expect = new int[N];
        for (int i = 0; i < N; i++) {
            expect[i] = arr[i] = r.nextInt(3);
        }
        Arrays.sort(expect);
        getSorter().sort(arr);
        Assert.assertArrayEquals(expect,arr);
    }

    @Test
    public void testAlreadySort() {
        int N = 10000;
        int[] arr = new int[N], expect = new int[N];
        for (int i = 0; i < N; i++) {
            expect[i] = arr[i] = i;
        }
        getSorter().sort(arr);
        Assert.assertArrayEquals(expect,arr);
    }

    @Test
    public void testAlreadyReverseSort() {
        int N = 10000;
        int[] arr = new int[N], expect = new int[N];
        for (int i = 0; i < N; i++) {
            expect[i] = arr[i] = N - i;
        }
        Arrays.sort(expect);
        getSorter().sort(arr);
        Assert.assertArrayEquals(expect,arr);
    }

    @Test
    public void testOneElement() {
        int[] arr = {1};
        getSorter().sort(arr);
        Assert.assertArrayEquals(new int[]{1},arr);
    }

    @Test
    public void testEmpty() {
        int[] arr = {};
        getSorter().sort(arr);
        Assert.assertArrayEquals(new int[]{},arr);
    }

    @Test
    public void testRandom() {
        Random r = new Random();
        int N = 100000;
        int[] arr = new int[N], expect = new int[N];
        for (int i = 0; i < N; i++) {
            expect[i] = arr[i] = (-N/2) + r.nextInt(N);
        }
        Arrays.sort(expect);
        getSorter().sort(arr);
        Assert.assertArrayEquals(expect,arr);
    }
}

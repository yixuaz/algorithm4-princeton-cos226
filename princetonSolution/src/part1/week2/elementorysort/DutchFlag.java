package part1.week2.elementorysort;

import java.util.Arrays;
import java.util.Random;

public class DutchFlag {
    private int[] buckets;

    public DutchFlag(int n) {
        this.buckets = new int[n];
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            buckets[i] = r.nextInt(3);
        }
    }

    public void swap(int i, int j) {
        int tmp = buckets[i];
        buckets[i] = buckets[j];
        buckets[j] = tmp;
    }

    public int color(int i) {
        return buckets[i];
    }

    @Override
    public String toString() {
        return Arrays.toString(buckets);
    }

    public boolean isSort() {
        for (int k = 1; k < buckets.length; k++) {
            if (color(k - 1) > color(k)) return false;
        }
        return true;
    }
}

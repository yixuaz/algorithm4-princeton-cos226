package part2.week3.radixsort;

public class AmericanFlagSort {
    public static void solve(int[] arr, int R) {
        int[] cnt = new int[R];
        for (int i : arr) {
            cnt[i]++;
        }
        int idx = 0;
        for (int i = 0; i < R; i++) {
            while (cnt[i]-- > 0)
                arr[idx++] = i;
        }
    }
}

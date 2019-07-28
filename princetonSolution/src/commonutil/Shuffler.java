package commonutil;

import java.util.Random;

public class Shuffler {
    public static void shuffle(int[] arr) {
        Random r = new Random();
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int k = r.nextInt(i + 1);
            int tmp = arr[i]; arr[i] = arr[k]; arr[k] = tmp;
        }
    }
    public static <T> void shuffle(T[] arr) {
        Random r = new Random();
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int k = r.nextInt(i + 1);
            T tmp = arr[i]; arr[i] = arr[k]; arr[k] = tmp;
        }
    }

    public static int[] getRandomArray(int n, int lowerBound, int upperBound) {
        int[] ret = new int[n];
        Random r = new Random();
        for (int i = 0; i < n; i++)
            ret[i] = lowerBound + r.nextInt(upperBound - lowerBound + 1);
        return ret;
    }
}

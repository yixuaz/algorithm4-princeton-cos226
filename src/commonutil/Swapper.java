package commonutil;

public class Swapper {
    public static void swap(int[] A, int i, int j) {
        int tmp  = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }
    public static <T> void swap(T[] A, int i, int j) {
        T tmp  = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }
}

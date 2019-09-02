package part1.week3.quicksort;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static commonutil.Shuffler.getRandomArray;

public class SelectionInTwoSortedArrayTest {
    private final long oneMilliSec = 1_000_000;

    private void checkOneSituation(int[] A, int splitPos,int[][] input) {
        input[0] = Arrays.copyOfRange(A,0,splitPos);
        input[1] = Arrays.copyOfRange(A,splitPos,A.length);
        Arrays.sort(A);
        Arrays.sort(input[0]);
        Arrays.sort(input[1]);
    }
    @Test
    public void basicTest() {
        int[] A1 = {1,2,3,4,5};
        int[] A2 = {6,7,8,9,10};
        Assert.assertEquals(5,
                SelectionInTwoSortedArray.getHalfOfNthElementInTwoSameLengthArray(A1,A2));
        Assert.assertEquals(5,
                SelectionInTwoSortedArray.getHalfOfNthElementInTwoArray(A1,A2));
        for (int i = 1; i <= 10; i++) {
            Assert.assertEquals(i,
                    SelectionInTwoSortedArray.getKthElementInTwoArray(A1,A2,i));
        }
    }
    @Test
    public void basicTest2() {
        int[] A1 = {1,3,5,7,9};
        int[] A2 = {2,4,6,8,10};
        Assert.assertEquals(5,
                SelectionInTwoSortedArray.getHalfOfNthElementInTwoSameLengthArray(A1,A2));
        Assert.assertEquals(5,
                SelectionInTwoSortedArray.getHalfOfNthElementInTwoArray(A1,A2));
        for (int i = 1; i <= 10; i++) {
            Assert.assertEquals(i,
                    SelectionInTwoSortedArray.getKthElementInTwoArray(A1,A2,i));
        }
    }
    @Test
    public void getHalfOfNthElementInTwoSameLengthArray() {
        int N = 100000;
        for (int i = 10; i <= N * 5; i *= 2) {
            int[] A = getRandomArray(N,-i/2,i/2);
            int[][] input = new int[2][];
            checkOneSituation(A,N/2,input);
            long st = System.nanoTime();
            Assert.assertEquals(A[N/2 - 1],
                    SelectionInTwoSortedArray.getHalfOfNthElementInTwoSameLengthArray(input[0],input[1]));
            Assert.assertTrue( (System.nanoTime() - st) < oneMilliSec);
        }
    }

    @Test
    public void getHalfOfNthElementInTwoArray() {
        int N = 100000;
        Random r = new Random();
        for (int i = 10; i <= N * 5; i += r.nextInt(i)) {
            int[] A = getRandomArray(N,-i/2,i/2);
            int[][] input = new int[2][];
            checkOneSituation(A,r.nextInt(A.length),input);
            long st = System.nanoTime();
            Assert.assertEquals(A[N/2 - 1],
                    SelectionInTwoSortedArray.getHalfOfNthElementInTwoArray(input[0],input[1]));
            Assert.assertTrue( (System.nanoTime() - st)< oneMilliSec);
        }
    }

    @Test
    public void getKthElementInTwoArray() {
        int N = 100000;
        Random r = new Random();
        for (int i = 10; i <= N * 5; i += r.nextInt(i)) {
            int[] A = getRandomArray(N,-i/2,i/2);
            int[][] input = new int[2][];
            checkOneSituation(A,r.nextInt(A.length),input);

            int k = r.nextInt(A.length);
            long st = System.nanoTime();
            Assert.assertEquals(A[k],
                    SelectionInTwoSortedArray.getKthElementInTwoArray(input[0],input[1],k + 1));
            Assert.assertTrue( (System.nanoTime() - st) < oneMilliSec);
        }
    }
}
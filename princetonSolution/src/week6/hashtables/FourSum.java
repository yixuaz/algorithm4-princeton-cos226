package week6.hashtables;

import java.util.HashMap;
import java.util.Map;

public class FourSum {
    private static class Pair {
        final int num1, num2;
        public Pair(int num1, int num2) {
            this.num1 = num1;
            this.num2 = num2;
        }
        public boolean noIntersect(Pair that) {
            return this.num1 != that.num1 && this.num1 != that.num2
                &&  this.num2 != that.num1 && this.num2 != that.num2;
        }

    }
    public static boolean solveInN2(int[] a) {
        Map<Integer,Pair> map = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                int need = - a[i] - a[j];
                Pair cur = new Pair(i, j);
                if (map.containsKey(need)) {
                    if (map.get(need).noIntersect(cur)) {
                        return true;
                    }
                }
                map.putIfAbsent(-need, cur);
            }
        }
        return false;
    }
}

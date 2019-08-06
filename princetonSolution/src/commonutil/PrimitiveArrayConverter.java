package commonutil;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PrimitiveArrayConverter {
    public static int[] convertToIntArray(Collection<Integer> in) {
        int[] ans = new int[in.size()];
        int idx = 0;
        for (int i : in) {
            ans[idx++] = i;
        }
        return ans;
    }
    public static long[] convertToLongArray(Collection<Long> in) {
        long[] ans = new long[in.size()];
        int idx = 0;
        for (long i : in) {
            ans[idx++] = i;
        }
        return ans;
    }

}

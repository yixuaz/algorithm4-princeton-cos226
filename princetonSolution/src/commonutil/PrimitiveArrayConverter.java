package commonutil;

import java.util.List;

public class PrimitiveArrayConverter {
    public static int[] convertToIntArray(List<Integer> in) {
        int[] ans = new int[in.size()];
        int idx = 0;
        for (int i : in) {
            ans[idx++] = i;
        }
        return ans;
    }

}

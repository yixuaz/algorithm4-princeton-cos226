package part1.week2.elementorysort;

/**
 * the idea behind it is more similar to the quick sort partition.
 * we keep 3 pointer when is the boundary of 0 and 1,
 * one is boundary of unknown and 1, one is boundary of unknown and 2.
 * so we move the middle pointer, to call color, know what the color of this unknown cell.
 * then decide to swap to and last pointer(keep 2) or first pointer(keep 0).
 */
public class DutchFlagSorter {
    public static void sort(DutchFlag flag, int n) {
        int i = 0, j = n - 1;
        for (int k = 0; k <= j;) {
            if (flag.color(k) == 1) {
                k++;
            } else if (flag.color(k) == 0) {
                flag.swap(i++, k++);
            } else {
                flag.swap(k, j--);
            }
        }
    }
}

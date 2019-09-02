package part1.week2.elementorysort;

public class DutchFlagSorter {
    public static void sort(DutchFlag flag, int n) {
        int i = 0, j = n - 1;
        for (int k = 0; k <= j; ) {
            if (flag.color(k) == 1) {
                k++;
            } else if (flag.color(k) == 0) {
                flag.swap(i++,k++);
            } else {
                flag.swap(k,j--);
            }
        }
    }
}

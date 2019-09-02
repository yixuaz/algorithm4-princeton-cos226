package commonutil;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class QuickSelect {

    public static <E> E findMedian(List<E> input, Comparator<E> comparator) {
        int k = (input.size() - 1) / 2;
        return quickSelect(input, k, 0, input.size() - 1, comparator);
    }

    private static <E> E quickSelect(List<E> input, int k, int st, int ed, Comparator<E> comparator) {
        E pivot = input.get(st);
        int p = st;
        for (int i = st + 1; i <= ed; i++) {
            if (comparator.compare(input.get(i), pivot) < 0) {
                swap(input,++p, i);
            }
        }
        swap(input,p,st);
        if (k < p) return quickSelect(input, k, st, p - 1, comparator);
        else if (k == p) return input.get(p);
        return quickSelect(input, k, p + 1, ed, comparator);
    }

    public static <E extends Comparable<E>> E findMedian(List<E> input) {
        int k = (input.size() - 1) / 2;
        return quickSelect(input, k, 0, input.size() - 1);
    }
    public static <E extends Comparable<E>> E findKth(List<E> input, int k) {
        return quickSelect(input, k, 0, input.size() - 1);
    }

    private static <E extends Comparable<E>> E quickSelect(List<E> input, int k, int st, int ed) {
        E pivot = input.get(st);
        int p = st;
        for (int i = st + 1; i <= ed; i++) {
            if (input.get(i).compareTo(pivot) < 0) {
                swap(input,++p, i);
            }
        }
        swap(input,p,st);
        if (k < p) return quickSelect(input, k, st, p - 1);
        else if (k == p) return input.get(p);
        return quickSelect(input, k, p + 1, ed);
    }

    private static void swap(List input, int i, int j) {
        Object tmp = input.get(i);
        input.set(i, input.get(j));
        input.set(j, tmp);
    }
}

package part1.week5.geoapp;

import part1.week5.bst.RedBlackBSTWithNoExtraMemory;

import java.util.ArrayList;
import java.util.List;

public class RangeSearchBST<Key extends Comparable<Key>, Val> extends RedBlackBSTWithNoExtraMemory<Key, Val> {
    public int size(Key lo, Key hi) {
        if (lo == null || hi == null || lo.compareTo(hi) > 0)
            throw new IllegalArgumentException("input is not valid");
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        return rank(hi) - rank(lo);
    }

    public List<Key> search(Key lo, Key hi) {
        if (lo == null || hi == null || lo.compareTo(hi) > 0)
            throw new IllegalArgumentException("input is not valid");
        List<Key> result = new ArrayList<>();
        search(root, lo, hi, result);
        return result;
    }

    private void search(Node cur, Key lo, Key hi, List<Key> result) {
        if (cur == null) return;
        int compareToLow = cur.key.compareTo(lo), compareToHigh = cur.key.compareTo(hi);
        if (compareToLow > 0) {
            search(cur.left, lo, hi, result);
        }
        if (compareToLow >= 0 && compareToHigh <= 0) {
            result.add(cur.key);
        }
        if (compareToHigh < 0) {
            search(cur.right, lo, hi, result);
        }
    }
}

package part1.week3.mergesort;

import commonutil.ISorter;
import commonutil.MergeSorter;
import commonutil.SorterTest;

public class MergeWithHalfArrayTest extends SorterTest {
    @Override
    public ISorter getSorter() {
        return new MergeSorter();
    }

}
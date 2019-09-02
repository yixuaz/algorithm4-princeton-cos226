package part2.week3.radixsort;

import commonutil.ISorter;
import commonutil.SorterTest;

public class MSDRadixSorterTest extends SorterTest {

    @Override
    public ISorter getSorter() {
        return new MSDRadixSorter();
    }
}
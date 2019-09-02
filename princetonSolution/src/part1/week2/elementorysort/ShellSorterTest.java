package part1.week2.elementorysort;

import commonutil.ISorter;
import commonutil.SorterTest;

public class ShellSorterTest extends SorterTest {
    @Override
    public ISorter getSorter() {
        return new ShellSorter();
    }
}
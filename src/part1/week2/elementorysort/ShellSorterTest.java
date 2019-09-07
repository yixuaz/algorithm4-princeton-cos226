package part1.week2.elementorysort;

import commonutil.ISorter;
import commonutil.SorterTest;
import org.junit.Rule;
import org.junit.rules.Timeout;

public class ShellSorterTest extends SorterTest {
    @Rule
    public Timeout timeout = Timeout.millis(100);

    @Override
    public ISorter getSorter() {
        return new ShellSorter();
    }
}
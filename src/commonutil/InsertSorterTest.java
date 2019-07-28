package commonutil;

import static org.junit.Assert.*;

public class InsertSorterTest extends SorterTest{

    @Override
    public ISorter getSorter() {
        return new InsertSorter();
    }
}
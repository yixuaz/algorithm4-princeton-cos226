package commonutil;

public class InsertSorterTest extends SorterTest{

    @Override
    public ISorter getSorter() {
        return new InsertSorter();
    }
}
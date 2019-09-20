package part2.week4.substringsearch.extracredit;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class SearchMultiplePatternTest {
    @Test
    public void basicTest() {
        SearchMultiplePattern search = new SearchMultiplePattern(Arrays.asList("abc", "def", "app", "cat"));
        Assert.assertEquals("abc", search.query("fhasjkfhabcqwe"));
        Assert.assertEquals("def", search.query("fhadefghabcqwe"));
        Assert.assertEquals("app", search.query("fhappfashabcqwe"));
        Assert.assertEquals("cat", search.query("fhasjkfhabqwecat"));
    }
}
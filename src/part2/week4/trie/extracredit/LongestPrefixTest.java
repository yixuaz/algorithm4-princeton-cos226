package part2.week4.trie.extracredit;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class LongestPrefixTest {

    @Test
    public void basicTest() {
        LongestPrefix longestPrefix = new LongestPrefix(Arrays.asList("128",
                "128.112",
                "128.112.055",
                "128.112.055.15",
                "128.112.136",
                "128.112.155.11",
                "128.112.155.13",
                "128.222",
                "128.222.136"));
        Assert.assertEquals("128.112.136", longestPrefix.solve("128.112.136.11"));
        Assert.assertEquals("128.112", longestPrefix.solve("128.112.100.16"));
        Assert.assertEquals("128", longestPrefix.solve("128.166.123.45"));
    }

}
package part2.week3.maxflow.extracredit;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BipartiteMatchingTest {
    @Test
    public void matchTest() {
        Map<String, Set<String>> input = new HashMap<>();
        input.put("Alice", new HashSet<>(Arrays.asList("Adobe", "Amazon", "Google")));
        input.put("Bob", new HashSet<>(Arrays.asList("Adobe", "Amazon")));
        input.put("Carol", new HashSet<>(Arrays.asList("Adobe", "Facebook", "Google")));
        input.put("Dave", new HashSet<>(Arrays.asList("Amazon", "Yahoo")));
        input.put("Eliza", new HashSet<>(Arrays.asList("Amazon", "Yahoo")));
        Map<String, String> test = BipartiteMatching.solve(input);
        Assert.assertTrue(!test.isEmpty());
        System.out.println(test);
    }
    @Test
    public void notMatchTest() {
        Map<String, Set<String>> input = new HashMap<>();
        input.put("Alice", new HashSet<>(Arrays.asList("Adobe", "Amazon", "Facebook", "Google")));
        input.put("Bob", new HashSet<>(Arrays.asList("Amazon", "Yahoo")));
        input.put("Carol", new HashSet<>(Arrays.asList("Adobe", "Facebook", "Google")));
        input.put("Dave", new HashSet<>(Arrays.asList("Amazon", "Yahoo")));
        input.put("Eliza", new HashSet<>(Arrays.asList("Amazon", "Yahoo")));
        Map<String, String> test = BipartiteMatching.solve(input);
        Assert.assertTrue(test.isEmpty());
    }

}
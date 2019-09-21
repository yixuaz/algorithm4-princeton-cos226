package part2.week5.regularexpression;

import org.junit.Assert;
import org.junit.Test;

public class ExtensionNFATest {
    @Test
    public void basicTest() {
        String regex = "(d|e)*(a|b.|c)+g";
        ExtensionNFA nfa = new ExtensionNFA(regex);
        Assert.assertTrue(nfa.recognizes("ag"));
        Assert.assertTrue(nfa.recognizes("bcg"));
        Assert.assertTrue(nfa.recognizes("cg"));
        Assert.assertTrue(nfa.recognizes("bzg"));
        Assert.assertTrue(nfa.recognizes("dag"));
        Assert.assertTrue(nfa.recognizes("decg"));
        Assert.assertTrue(nfa.recognizes("eeecg"));
        Assert.assertTrue(nfa.recognizes("dddb0g"));
        Assert.assertTrue(nfa.recognizes("eabbbeag"));
        Assert.assertFalse(nfa.recognizes("adg"));
        Assert.assertFalse(nfa.recognizes(""));
        Assert.assertFalse(nfa.recognizes("deg"));
        Assert.assertFalse(nfa.recognizes("eeg"));
        Assert.assertFalse(nfa.recognizes("ab"));
        Assert.assertFalse(nfa.recognizes("bbb"));
        Assert.assertFalse(nfa.recognizes("b"));
        Assert.assertFalse(nfa.recognizes("d"));
    }

}
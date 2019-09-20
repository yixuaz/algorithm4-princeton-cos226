package part2.week4.substringsearch;

/**
 * Longest palindromic substring. Given a string ss,
 * find the longest substring that is a palindrome in expected linearithmic time.
 *
 * Signing bonus: Do it in linear time in the worst case.
 */
public class LongestPalindromicSubStr {

    // assumption, input str only have lower case a b c d letter
    public static String findTimeNlgN(String cur) {
        // TODO: ADD YOUR CODE HERE
        return null;
    }

    public static String findTimeN(String s) {
        // TODO: ADD YOUR CODE HERE
        return null;
    }

    private static int st = 0;
    private static int maxLen = 0;

    public static String findTimeN2(String s) {
        st = maxLen = 0;
        char[] cs = s.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            find(cs, i, i);
            find(cs, i, i + 1);
        }
        return s.substring(st, st + maxLen);
    }

    private static void find(char[] cs, int i, int j) {
        while (i >= 0 && j < cs.length) {
            if (cs[i] != cs[j]) break;
            i--;
            j++;
        }
        if (j - i - 1 > maxLen) {
            maxLen = j - i - 1;
            st = i + 1;
        }
    }
}



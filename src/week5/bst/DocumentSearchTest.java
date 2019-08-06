package week5.bst;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DocumentSearchTest {
    Random r = new Random();
    @Test
    public void basicTest() {
        List<String> document = convert("abcdebdde");
        List<String> query = convert("bde");
        Assert.assertEquals(4,DocumentSearch.solve(document,query));
    }
    private List<String> convert(String s) {
        List<String> res = new ArrayList<>();
        for (char c : s.toCharArray()) {
            res.add(c+"ok");
        }
        return res;
    }
    @Test
    public void basicTest2() {
        List<String> document = convert("hpsrhgogezyfrwfrejytjkzvgpjnqil");
        List<String> query = convert("ggj");
        Assert.assertEquals(13,DocumentSearch.solve(document,query));
    }

    @Test
    public void basicTest3() {
        List<String> document = convert("jmeqksfrsdcmsiwvaovztaqenprpvnbstl");
        List<String> query = convert("m");
        Assert.assertEquals(1,DocumentSearch.solve(document,query));
    }

    @Test
    public void basicTest4() {
        List<String> document = convert("wcbsuiyzacfgrqsqsnodwmxzkz");
        List<String> query = convert("xwqe");
        Assert.assertEquals(-1,DocumentSearch.solve(document,query));
    }

    @Test
    public void randomTest() {
        int N = 1000, n = 500, m = 100;
        for (int i = 0; i < N; i++) {
            String document = randomString(1 + r.nextInt(n));
            String query = randomString(1 + r.nextInt(m));
            List<String> documents = convert(document);
            List<String> querys = convert(query);
            Assert.assertEquals(expect2(document,query),DocumentSearch.solve(documents,querys));
        }
    }

    private String randomString(int len){
        String AB = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(len);
        for( int i = 0; i < len; i++ )
            sb.append(AB.charAt(r.nextInt(AB.length())));
        return sb.toString();
    }

    private int expect(String S, String T) {
        int m = S.length(), n = T.length(), len = m - n;
        char[] ts = T.toCharArray();

        int idx = -1, minLen = Integer.MAX_VALUE;
        while (idx < len) {
            for (int tidx = 0; tidx < n; tidx++) {
                idx = S.indexOf(ts[tidx], idx + 1);
                if (idx == -1) return minLen == Integer.MAX_VALUE ? -1 : minLen;
            }
            int end = ++idx;
            for (int tidx = n - 1; tidx >= 0; tidx--) {
                idx = S.lastIndexOf(ts[tidx], idx - 1);
            }
            minLen = Math.min(end - idx, minLen);
            idx++;
        }
        return minLen == Integer.MAX_VALUE ? -1 : minLen;
    }
    private int expect2(String S, String T) {
        int m = T.length(), n = S.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j + 1;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (T.charAt(i - 1) == S.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = dp[i][j - 1];
                }
            }
        }
        int len = n + 1;
        for (int j = 1; j <= n; j++) {
            if (dp[m][j] == 0) continue;
            len = Math.min(len,j - dp[m][j] + 1);
        }
        return len == n + 1 ? -1 : len;
    }
}
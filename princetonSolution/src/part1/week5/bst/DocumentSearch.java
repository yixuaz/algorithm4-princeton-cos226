package part1.week5.bst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *this problem could be solved by two pointer, and dynamic programming.
 * the idea of two pointer is easy, first lock the first query word in document position,
 * then find until the last query word,
 * then from last query word in document position go back to find the first query word.
 * eg (query word: a,b  document word: a,c,c,a,b)
 * i start at 0, then j end at 4, in the back finding,
 * i end at 3. then this is one possible answer, then move the i to the next a position.
 *
 * dynamic programing ,we use dp array to save the position.
 * dp[i][j] means in ith query word, and in the jth document word, the distance to beginning query word position.
 * the state transition equation is word i = word j, then dp[i][j] = dp[i - 1][j -1] , else
 * dp[i][j] = dp[i][j - 1]
 * eg. (a,c,c,a,b) query word(a,b)
 * dp[0] = {0,1,2,3,4,5}
 * dp[1] = {0,0, 0, 0, 3, 4}
 *
 * the ans is that we find the j - dp[2][i] +1 with min value;
 */
public class DocumentSearch {
    public static int solve(List<String> documents, List<String> queryWords) {
        int n = documents.size(), m = queryWords.size();
        Map<String, List<Integer>> word2IdxList = new HashMap<>();
        int idx = 0;
        for (String w : documents) {
            word2IdxList.putIfAbsent(w, new ArrayList<>());
            word2IdxList.get(w).add(idx++);
        }
        List<Integer> firstQueryIdx = word2IdxList.get(queryWords.get(0));
        if (firstQueryIdx == null) return -1;
        int minL = Integer.MAX_VALUE;
        for (int k = 0; k < firstQueryIdx.size(); k++) {
            int st = firstQueryIdx.get(k), ed = st + 1;
            for (int i = 1; i < m; i++) {
                List<Integer> ithQueryIdx = word2IdxList.get(queryWords.get(i));
                if (ithQueryIdx == null) return -1;
                ed = Collections.binarySearch(ithQueryIdx, ed);
                if (ed < 0) ed = -ed - 1;
                if (ed == ithQueryIdx.size())
                    return minL == Integer.MAX_VALUE ? -1 : minL;
                ed = ithQueryIdx.get(ed) + 1;
            }
            assert queryWords.get(m - 1).equals(documents.get(ed - 1));
            st = ed - 2;
            for (int i = m - 2; i >= 0; i--) {
                List<Integer> ithQueryIdx = word2IdxList.get(queryWords.get(i));
                st = Collections.binarySearch(ithQueryIdx, st);
                if (st < 0) st = -st - 2;
                k = st;
                st = ithQueryIdx.get(st) - 1;
            }
            assert queryWords.get(0).equals(documents.get(st + 1));
            minL = Math.min(minL, ed - (1 + st));
        }
        return minL == Integer.MAX_VALUE ? -1 : minL;
    }
}

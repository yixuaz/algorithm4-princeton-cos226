package part1.week5.bst;

import java.util.*;

public class DocumentSearch {
    public static int solve(List<String> documents, List<String> queryWords) {
        int n = documents.size(), m = queryWords.size();
        Map<String,List<Integer>> word2IdxList = new HashMap<>();
        int idx = 0;
        for (String w : documents) {
            word2IdxList.putIfAbsent(w,new ArrayList<>());
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
                ed = Collections.binarySearch(ithQueryIdx,ed);
                if (ed < 0) ed = -ed - 1;
                if (ed == ithQueryIdx.size())
                    return minL == Integer.MAX_VALUE ?  -1 : minL;
                ed = ithQueryIdx.get(ed) + 1;
            }
            assert queryWords.get(m - 1).equals(documents.get(ed - 1));
            st = ed - 2;
            for (int i = m - 2; i >= 0; i--) {
                List<Integer> ithQueryIdx = word2IdxList.get(queryWords.get(i));
                st = Collections.binarySearch(ithQueryIdx,st);
                if (st < 0) st = -st - 2;
                k = st;
                st = ithQueryIdx.get(st) - 1;
            }
            assert queryWords.get(0).equals(documents.get(st + 1));
            minL = Math.min(minL,ed - (++st));
        }
        return minL == Integer.MAX_VALUE ?  -1 : minL;
    }
}

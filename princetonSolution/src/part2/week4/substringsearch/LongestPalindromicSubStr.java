package part2.week4.substringsearch;

import edu.princeton.cs.algs4.RabinKarp;

public class LongestPalindromicSubStr {

    // assumption, input str only have lower case a b c d letter
    public static String findTimeNlgN(String cur) {
        char[] cs = cur.toCharArray();
        int s = 1, e = cs.length;
        int[] ans = new int[2];
        while (s <= e) {
            int mid = s + (e - s) / 2;
            if (existsPal(mid, cs, ans)) {
                s = mid + 1;
            } else if (mid < e && existsPal(mid + 1, cs, ans)) {
                s = mid + 2;
            } else {
                e = mid - 1;
            }
        }
        return cur.substring(ans[0], ans[1]);
    }

    // las vegas
    private static boolean existsPal(int mid, char[] cs, int[] ans) {
        long ori = 0, rev = 0, base = 1;
        int M = 1000000007, R = 4;
        for (int i = 0; i < mid; i++) {
            ori = (ori * R + (cs[i] - 'a')) % M;
            rev = (rev * R + (cs[mid - 1 - i] - 'a')) % M;
        }
        for (int i = 1; i < mid; i++) base = base * R % M;
        if (ori == rev && checkPal(cs, 0, mid - 1, ans)) {
            return true;
        }
        long preBase = 1;
        for (int i = mid; i < cs.length; i++) {
            int delete = cs[i - mid] - 'a';
            int add = (cs[i] - 'a');
            ori = (ori - (delete * base % M) + M) % M;
            ori = (ori * R + add * preBase) * R % M;
            rev = (rev - (delete * preBase % M) + M) % M;
            base = base * R % M;
            preBase = preBase * R % M;
            rev = (rev + base * add) % M;
            if (ori == rev && checkPal(cs, i - mid + 1, i, ans))
                return true;

        }
        return false;
    }

    private static boolean checkPal(char[] cs, int i, int j, int[] ans) {
        int oriI = i, oriJ = j;
        while (i < j) {
            if (cs[i++] != cs[j--]) return false;
        }
        ans[0] = oriI;
        ans[1] = oriJ + 1;
        return true;
    }

    public static String findTimeN(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            sb.append('#').append(s.charAt(i));
        }
        sb.append('#');
        char[] T = sb.toString().toCharArray();
        int[] P = new int[T.length];
        int center = 0, boundary = 0, maxLen = 0, resCenter = 0;
        for (int i = 1; i < T.length - 1; i++) {
            int iMirror = 2 * center - i;
            P[i] = (i < boundary) ? Math.min(boundary - i, P[iMirror]): 0;
            while (i - P[i] - 1 >= 0 && i + P[i] + 1 < T.length
                    && T[i - P[i] - 1] == T[i + P[i] + 1])
                P[i]++;
            if (i + P[i] > boundary) {
                boundary = i + P[i];
                center = i;
            }
            if (P[i] > maxLen) {
                maxLen = P[i];
                resCenter = i;
            }
        }
        int start = (resCenter - maxLen) / 2;
        return s.substring(start, start + maxLen);
    }

    private static int st = 0;
    private static int maxLen = 0;
    public static String findTimeN2(String s) {
        st = maxLen = 0;
        char[] cs = s.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            find(cs,i,i);
            find(cs,i,i+1);
        }
        return s.substring(st,st+maxLen);
    }
    private static void  find(char[] cs, int i, int j) {
        while (i >= 0 && j < cs.length) {
            if (cs[i] != cs[j]) break;
            i--;j++;
        }
        if (j - i - 1 > maxLen) {
            maxLen = j - i - 1;
            st = i + 1;
        }
    }
}



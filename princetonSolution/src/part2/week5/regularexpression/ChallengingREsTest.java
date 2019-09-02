package part2.week5.regularexpression;

import org.junit.Assert;
import org.junit.Test;

public class ChallengingREsTest {

    @Test
    public void EXCEPT_11_OR_111() {
        Assert.assertEquals(false,ChallengingREs.EXCEPT_11_OR_111.match("11"));
        Assert.assertEquals(false,ChallengingREs.EXCEPT_11_OR_111.match("111"));
        Assert.assertEquals(true,ChallengingREs.EXCEPT_11_OR_111.match("1111"));
        Assert.assertEquals(true,ChallengingREs.EXCEPT_11_OR_111.match("101"));
    }
    @Test
    public void ODD_BIT_POS_IS_1() {
        Assert.assertEquals(true,ChallengingREs.ODD_BIT_POS_IS_1.match("1"));
        Assert.assertEquals(true,ChallengingREs.ODD_BIT_POS_IS_1.match("10"));
        Assert.assertEquals(true,ChallengingREs.ODD_BIT_POS_IS_1.match("11"));
        Assert.assertEquals(true,ChallengingREs.ODD_BIT_POS_IS_1.match("111"));
        Assert.assertEquals(false,ChallengingREs.ODD_BIT_POS_IS_1.match("011"));
        Assert.assertEquals(true,ChallengingREs.ODD_BIT_POS_IS_1.match("1010"));
        Assert.assertEquals(false,ChallengingREs.ODD_BIT_POS_IS_1.match("1101"));
        Assert.assertEquals(true,ChallengingREs.ODD_BIT_POS_IS_1.match(""));
    }
    @Test
    public void AT_LEAST_TWO_0S_AT_MOST_1() {
        Assert.assertEquals(false,ChallengingREs.AT_LEAST_TWO_0S_AT_MOST_1.match("1"));
        Assert.assertEquals(false,ChallengingREs.AT_LEAST_TWO_0S_AT_MOST_1.match("10"));
        Assert.assertEquals(true,ChallengingREs.AT_LEAST_TWO_0S_AT_MOST_1.match("100"));
        Assert.assertEquals(true,ChallengingREs.AT_LEAST_TWO_0S_AT_MOST_1.match("010"));
        Assert.assertEquals(false,ChallengingREs.AT_LEAST_TWO_0S_AT_MOST_1.match("011000"));
        Assert.assertEquals(true,ChallengingREs.AT_LEAST_TWO_0S_AT_MOST_1.match("01000"));
        Assert.assertEquals(true,ChallengingREs.AT_LEAST_TWO_0S_AT_MOST_1.match("00001"));
        Assert.assertEquals(false,ChallengingREs.AT_LEAST_TWO_0S_AT_MOST_1.match("100001"));
    }
    @Test
    public void BINARY_INTEGER_ARE_A_MULTIPLE_OF_3() {
        for (int i = 0; i < 100000; i++) {
            Assert.assertEquals(Integer.toBinaryString(i),i % 3 == 0,
                    ChallengingREs.BINARY_INTEGER_ARE_A_MULTIPLE_OF_3.match(Integer.toBinaryString(i)));
        }
    }
    @Test
    public void NO_TWO_CONSECUTIVE_1S() {
        for (int i = 0; i < 100000; i++) {
            String input = Integer.toBinaryString(i);
            Assert.assertEquals(!checkTwoConsecOne(input.toCharArray()),
                    ChallengingREs.NO_TWO_CONSECUTIVE_1S.match(input));
        }
    }
    private boolean checkTwoConsecOne(char[] in) {
        char pre = '0';
        for (char c : in) {
            if (c == '1' && pre == c) return true;
            pre = c;
        }
        return false;
    }

    @Test
    public void EQUAL_NUM_SUBSTR_FORM_01_AND_10() {
        for (int i = 0; i < 100000; i++) {
            String input = Integer.toBinaryString(i);
            Assert.assertEquals(is01_10CntSame(input.toCharArray()),
                    ChallengingREs.EQUAL_NUM_SUBSTR_FORM_01_AND_10.match(input));
        }

    }

    private boolean is01_10CntSame(char[] cs) {
        int cnt01 = 0, cnt10 = 0;
        for (int i = 1; i < cs.length; i++) {
            if (cs[i - 1] == '0' && cs[i] == '1') cnt01++;
            else if (cs[i - 1] == '1' && cs[i] == '0') cnt10++;
        }
        return cnt01 == cnt10;
    }
}
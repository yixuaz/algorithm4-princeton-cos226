package part2.week5.regularexpression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ChallengingREs {
    EXCEPT_11_OR_111 {
        @Override
        String getRegex() {
            return "(?!(11|111)$).+";
        }
    }, ODD_BIT_POS_IS_1 {
        @Override
        String getRegex() {
            return "(1[01])*1?";
        }
    }, EQUAL_NUMBER_0S_1S {
        @Override
        String getRegex() {
            return IMPOSSIBLE;
        }
    }, AT_LEAST_TWO_0S_AT_MOST_1 {
        @Override
        String getRegex() {
            return "(100+)|(0+10+)|(00+1)";
        }
    }, BINARY_INTEGER_ARE_A_MULTIPLE_OF_3 {
        @Override
        String getRegex() { // https://www.geeksforgeeks.org/check-binary-string-multiple-3-using-dfa/
            return "^0*((1(01*0)*1)*0*)*$";
        }
    }, NO_TWO_CONSECUTIVE_1S {
        @Override
        String getRegex() {
            return "(0*10+)*1?|0*";
        }
    }, PALINDROMES {
        @Override
        String getRegex() {
            return IMPOSSIBLE;
        }
    }, EQUAL_NUM_SUBSTR_FORM_01_AND_10 { // youtube yuFF0gXC6HU
        @Override
        String getRegex() {
            return "0+(1*0+)*|1+(0*1+)*|";
        }
    };
    private static final String IMPOSSIBLE = null;
    abstract String getRegex();
    public boolean match(String input) {
        if (getRegex() == IMPOSSIBLE) return false;
        return Pattern.matches(getRegex(), input);
    }
}

package part2.week5.regularexpression;

import java.util.regex.Pattern;

public enum ChallengingREs {
    EXCEPT_11_OR_111 {
        @Override
        String getRegex() {
            // TODO: ADD YOUR REGEX HERE IF IMPOSSIBLE RETURN null
            return "";
        }
    }, ODD_BIT_POS_IS_1 {
        @Override
        String getRegex() {
            // TODO: ADD YOUR REGEX HERE IF IMPOSSIBLE RETURN null
            return "";
        }
    }, EQUAL_NUMBER_0S_1S {
        @Override
        String getRegex() {
            // TODO: ADD YOUR REGEX HERE IF IMPOSSIBLE RETURN null
            return "";
        }
    }, AT_LEAST_TWO_0S_AT_MOST_1 {
        @Override
        String getRegex() {
            // TODO: ADD YOUR REGEX HERE IF IMPOSSIBLE RETURN null
            return "";
        }
    }, BINARY_INTEGER_ARE_A_MULTIPLE_OF_3 {
        @Override
        String getRegex() { // https://www.geeksforgeeks.org/check-binary-string-multiple-3-using-dfa/
            // TODO: ADD YOUR REGEX HERE IF IMPOSSIBLE RETURN null
            return "";
        }
    }, NO_TWO_CONSECUTIVE_1S {
        @Override
        String getRegex() {
            // TODO: ADD YOUR REGEX HERE IF IMPOSSIBLE RETURN null
            return "";
        }
    }, PALINDROMES {
        @Override
        String getRegex() {
            // TODO: ADD YOUR REGEX HERE IF IMPOSSIBLE RETURN null
            return "";
        }
    }, EQUAL_NUM_SUBSTR_FORM_01_AND_10 { // youtube yuFF0gXC6HU
        @Override
        String getRegex() {
            // TODO: ADD YOUR REGEX HERE IF IMPOSSIBLE RETURN null
            return "";
        }
    };

    private static final String IMPOSSIBLE = null;
    abstract String getRegex();
    public boolean match(String input) {
        if (getRegex() == IMPOSSIBLE) return false;
        return Pattern.matches(getRegex(), input);
    }
}

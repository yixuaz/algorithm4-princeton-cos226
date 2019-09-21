package part2.week5.regularexpression;

import java.util.regex.Pattern;

public class ExponentialSizeDFA {
    // https://www.owenstephens.co.uk/blog/2014/09/28/NFA_DFA.html
    // hint: nth-to-the-last bit equals 0
    public static boolean match(String input) {
        int n = input.length();
        return Pattern.matches("(0|1)*0(0|1){" + (n - 1) + "}", input);
    }
}

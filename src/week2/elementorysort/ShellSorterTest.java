package week2.elementorysort;

import commonutil.ISorter;
import commonutil.SorterTest;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

public class ShellSorterTest extends SorterTest {
    @Rule
    public Timeout timeout = Timeout.millis(100);
    @Override
    public ISorter getSorter() {
        return new ShellSorter();
    }
}
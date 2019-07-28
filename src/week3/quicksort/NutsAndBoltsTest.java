package week3.quicksort;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.Timeout;
import week3.quicksort.NutsAndBolts.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class NutsAndBoltsTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(100);

    public void match() {
        int N = 100000;
        for (int i = 1; i <= N; i = 3 * i + 1) {
            Bolt[] bolts = NutsAndBolts.buildBolts(i);
            Nut[] nuts = NutsAndBolts.buildNuts(i);
            NutsAndBolts.match(bolts, nuts);
            for (int j = 0; j < i; j++) {
                Assert.assertTrue(bolts[j].compareTo(nuts[j]) == 0);
                Assert.assertTrue(nuts[j].compareTo(bolts[j]) == 0);
            }
        }
    }

    @Test(expected = IllegalStateException.class)
    public void errorInput() {
        int N = 100000;
        Bolt[] bolts = NutsAndBolts.buildBolts(N);
        Nut[] nuts = NutsAndBolts.buildNuts(N);
        bolts[N/3] = bolts[N/4] = new Bolt(1);
        NutsAndBolts.match(bolts, nuts);
    }

    @Test(expected = IllegalStateException.class)
    public void errorInput2() {
        int N = 100000;
        Bolt[] bolts = NutsAndBolts.buildBolts(N);
        Nut[] nuts = NutsAndBolts.buildNuts(N);
        nuts[N/3] = nuts[N/4] = new Nut(N/2);
        NutsAndBolts.match(bolts, nuts);
    }

    @Test(expected = IllegalStateException.class)
    public void errorInput3() {
        int N = 100000;
        Bolt[] bolts = NutsAndBolts.buildBolts(N);
        Nut[] nuts = NutsAndBolts.buildNuts(N);
        bolts[N/3] = new Bolt(-1);
        NutsAndBolts.match(bolts, nuts);
    }

    @Test(expected = IllegalStateException.class)
    public void errorInput4() {
        int N = 100000;
        Bolt[] bolts = NutsAndBolts.buildBolts(N);
        Nut[] nuts = NutsAndBolts.buildNuts(N);
        nuts[N/4] = new Nut(2 * N);
        NutsAndBolts.match(bolts, nuts);
    }
}
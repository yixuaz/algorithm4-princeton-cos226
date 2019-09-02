package part1.week3.quicksort;

import static commonutil.Shuffler.shuffle;
import static commonutil.Swapper.swap;

public class NutsAndBolts {
    public static class Nut implements Comparable<Bolt>{
        private final int size;
        public Nut(int size) {
            this.size = size;
        }
        @Override
        public int compareTo(Bolt o) {
            if (size == o.size)
                return 0;
            return size < o.size ? -1 : 1;
        }
    }
    public static class Bolt implements Comparable<Nut>{
        private final int size;
        public Bolt(int size) {
            this.size = size;
        }
        @Override
        public int compareTo(Nut o) {
            if (size == o.size)
                return 0;
            return size < o.size ? -1 : 1;
        }
    }

    public static Nut[] buildNuts(int n) {
        Nut[] nuts = new Nut[n];
        for (int i = 0; i < n; i++) {
            nuts[i] = new Nut(i);
        }
        shuffle(nuts);
        return nuts;
    }
    public static Bolt[] buildBolts(int n) {
        Bolt[] bolts = new Bolt[n];
        for (int i = 0; i < n; i++) {
            bolts[i] = new Bolt(i);
        }
        shuffle(bolts);
        return bolts;
    }

    public static void match(Bolt[] bolts, Nut[] nuts) {
        //TODO: ADD YOUR CODE HERE
        //throw new IllegalStateException() when bolts and nuts cannot totally match
    }



}

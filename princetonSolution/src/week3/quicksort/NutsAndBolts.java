package week3.quicksort;

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
        if (bolts.length != nuts.length) {
            throw new IllegalArgumentException("bolts and nuts length should be same");
        }
        quickSort(bolts,nuts,0,bolts.length - 1);
    }

    private static void quickSort(Bolt[] bolts, Nut[] nuts, int s, int e) {
        if (s >= e) return;
        Bolt tarBolt = bolts[s];
        int i = s,p = s, j = e;
        int findMatch = 0;
        while (p <= j) {
            if (nuts[p].compareTo(tarBolt) < 0) {
                swap(nuts,i++,p++);
            } else if (nuts[p].compareTo(tarBolt) == 0) {
                findMatch++;
                p++;
            } else {
                swap(nuts,j--,p);
            }
        }
        if (findMatch != 1)
            throw new IllegalStateException("one nut can not been matched from bolts or can match more than one bolts");
        assert nuts[i].compareTo(tarBolt) == 0;
        Nut tarNut = nuts[i];
        int m = s;
        for (int k = s + 1; k <= e; k++) {
            if (bolts[k].compareTo(tarNut) < 0)
                swap(bolts,++m,k);
        }
        swap(bolts,m,s);
        assert bolts[m].compareTo(nuts[i]) == 0;
        quickSort(bolts,nuts,s,m - 1);
        quickSort(bolts,nuts,m + 1,e);
    }

}

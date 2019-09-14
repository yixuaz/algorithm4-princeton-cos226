package part2.week1.project.normal;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet myWordnet;

    public Outcast(WordNet wordnet) {
        myWordnet = wordnet;
    }         // constructor takes a WordNet object

    public String outcast(String[] nouns) {
        int maxDis = 0;
        int tar = 0, n = nouns.length;
        for (int i = 0; i < n; i++) {
            int curDis = 0;
            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                curDis += myWordnet.distance(nouns[i], nouns[j]);
            }
            if (curDis > maxDis) {
                maxDis = curDis;
                tar = i;
            }
        }
        return nouns[tar];
    }  // given an array of WordNet nouns, return an outcast

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    } // see test client below
}

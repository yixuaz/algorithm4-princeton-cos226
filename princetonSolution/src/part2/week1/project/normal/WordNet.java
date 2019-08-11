package part2.week1.project.normal;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordNet {
    private static final String COMMA = ",";

    private final List<String[]> vertexs; // id to word
    private final Map<String, Set<Integer>> syn2Id; // word 2 id
    private final SAP sap; // sap
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        checkNull(synsets, hypernyms);
        In in = new In(synsets);
        vertexs = new ArrayList<>();
        syn2Id = new HashMap<>();
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] words = line.split(COMMA);
            String[] curSynsets = words[1].split(" ");
            for (String curSynset : curSynsets) {
                syn2Id.putIfAbsent(curSynset, new HashSet<>());
                syn2Id.get(curSynset).add(vertexs.size());
            }
            vertexs.add(curSynsets);
        }
        Digraph digraph = new Digraph(vertexs.size());
        in = new In(hypernyms);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] ids = line.split(COMMA);
            for (int i = 1; i < ids.length; i++) {
                digraph.addEdge(Integer.parseInt(ids[0]), Integer.parseInt(ids[i]));
            }
        }
        DirectedCycle finder = new DirectedCycle(digraph);
        if (finder.hasCycle())
            throw new IllegalArgumentException("input has cycle");
        // check one root
        int zeroCnt = 0;
        for (int i = 0; i < digraph.V(); i++) {
            if (digraph.outdegree(i) == 0) {
                if (zeroCnt > 0) {
                    throw new IllegalArgumentException("more than one root");
                }
                zeroCnt++;
            }
        }
        sap = new SAP(digraph);
    }
    // check input is null
    private void checkNull(Object a, Object b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("input should not be null");
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return Collections.unmodifiableSet(syn2Id.keySet());
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException("input should not be null");
        return syn2Id.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        checkNull(nounA, nounB);
        if (!syn2Id.containsKey(nounA) || !syn2Id.containsKey(nounB))
            throw new IllegalArgumentException("input is not a noun");
        return sap.length(syn2Id.get(nounA), syn2Id.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        checkNull(nounA, nounB);
        if (!syn2Id.containsKey(nounA) || !syn2Id.containsKey(nounB))
            throw new IllegalArgumentException("input is not a noun");
        return String.join(" ",
                           vertexs.get(sap.ancestor(syn2Id.get(nounA), syn2Id.get(nounB))));
    }

}

package part2.week3.radixsort.suffixarray;

import part2.week4.trie.SuffixTree;

public class SuffixTreeSuffixArray implements SuffixArray{
    @Override
    public int[] getSuffixArray(String s) {
        SuffixTree suffixTree = new SuffixTree(s);
        return suffixTree.buildSuffixArray();
    }
}

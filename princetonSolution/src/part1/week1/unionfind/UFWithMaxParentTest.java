package part1.week1.unionfind;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;
import java.util.TreeSet;

public class UFWithMaxParentTest {

    @Test
    public void testUFCorrectness() {
        int N = 5000;
        UFWithMaxParent uf1 = new UFWithMaxParent(N);
        WeightedQuickUnionUF uf2 = new WeightedQuickUnionUF(N);
        Random r = new Random();
        for (int i = 0; i < N / 4; i++) {
            int k = r.nextInt(N), j = r.nextInt(N);
            uf1.union(k,j);
            uf2.union(k,j);
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                Assert.assertEquals(uf1.connected(i,j),uf2.connected(i,j));
            }
        }

    }

    @Test
    public void testFindMaxCorrectness() {
        int N = 10000;
        UFWithMaxParent uf = new UFWithMaxParent(N);
        Random r = new Random();
        int k = r.nextInt(N) / 2;
        TreeSet<Integer>[] treeSets = new TreeSet[k];
        for (int i = 0; i < k; i++) treeSets[i] = new TreeSet<>();
        for (int i = 0; i < N; i++) {
            int j = r.nextInt(k);
            treeSets[j].add(i);
        }
        for (int i = 0; i < k; i++) {
            int pre = -1;
            for (int j : treeSets[i]) {
                if (pre != -1) uf.union(pre,j);
                pre = j;
            }
        }
        for (int i = 0; i < k; i++) {
            for (int j : treeSets[i]) {
                Assert.assertEquals(uf.find(j),treeSets[i].last().intValue());
            }
        }
    }
}
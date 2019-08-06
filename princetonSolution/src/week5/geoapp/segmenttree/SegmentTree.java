package week5.geoapp.segmenttree;

import java.util.Arrays;

public class SegmentTree<E> {
    private final E[] tree;
    private final E[] extra;
    private final Merger<E> function;
    private final Merger<E> extraMerger;
    private final Updater<E> extraUpdater;
    private final int size;
    public SegmentTree(E[] data, Merger<E> function, Merger<E> extraMerger, Updater<E> extraUpdater) {
        if (function == null || data == null)
            throw new IllegalArgumentException("function and data could not be null");
        size = data.length;
        tree = (E[]) new Object[4 * size];
        extra = (E[]) new Object[4 * size];
        this.function = function;
        this.extraMerger = extraMerger;
        this.extraUpdater = extraUpdater;
        build(data.clone(),0, 0, size - 1);
    }

    private void build(E[] data, int curIdx, int st, int ed) {
        if (st == ed) {
            tree[curIdx] = data[st];
            return;
        }
        int mid = st + (ed - st) / 2;
        int leftIdx = leftChild(curIdx), rightIdx = rightChild(curIdx);
        build(data, leftIdx, st, mid);
        build(data, rightIdx, mid + 1, ed);
        tree[curIdx] = function.merge(tree[leftIdx], tree[rightIdx]);
    }

    private int leftChild(int idx) {
        return 2 * idx + 1;
    }

    private int rightChild(int idx) {
        return 2 * idx + 2;
    }

    public E query(int st, int ed) {
        if (st < 0 || ed >= size || st > ed)
            throw new IllegalArgumentException("range invalid");
        return query(0, 0, size - 1, st, ed);
    }

    private E query(int curIdx, int rangeSt, int rangeEd, int querySt, int queryEd) {
        if (querySt <= rangeSt && queryEd >= rangeEd) return tree[curIdx];
        pushDown(curIdx,rangeSt,rangeEd);
        int mid = rangeSt + (rangeEd - rangeSt) / 2;
        if (queryEd <= mid) return query(leftChild(curIdx), rangeSt, mid, querySt, queryEd);
        if (querySt > mid) return query(rightChild(curIdx), mid + 1, rangeEd, querySt, queryEd);
        return function.merge(query(leftChild(curIdx), rangeSt, mid, querySt, queryEd),
                query(rightChild(curIdx), mid + 1, rangeEd, querySt, queryEd));
    }

    public void set(int idx, E e) {
        if (idx < 0 || idx >= size)
            throw new IllegalArgumentException("idx invalid");
        set(0, 0, size - 1, idx, e);
    }

    private void set(int curIdx, int st, int ed, int tarIdx, E e) {
        if (st == ed) {
            tree[curIdx] = e;
            return;
        }
        int mid = st + (ed - st) / 2;
        int leftIdx = leftChild(curIdx), rightIdx = rightChild(curIdx);
        if (tarIdx <= mid) set(leftIdx, st, mid, tarIdx, e);
        else set(rightIdx, mid + 1, ed, tarIdx, e);
        tree[curIdx] = function.merge(tree[leftIdx], tree[rightIdx]);
    }

    public void updateRange(int st, int ed, E e) {
        if (extraUpdater == null || extraMerger == null)
            throw new IllegalStateException("extraUpdater OR extraMerger should not be null in range update");
        if (st < 0 || ed >= size || st > ed)
            throw new IllegalArgumentException("invalid range");
        updateRange(0, 0, size - 1, st, ed, e);
    }

    private void updateRange(int curIdx, int rangeSt, int rangeEd, int updateSt, int updateEd, E e) {
        if (rangeEd < updateSt || rangeSt > updateEd) return;
        if (updateSt <= rangeSt && updateEd >= rangeEd) {
            tree[curIdx] = extraUpdater.update(tree[curIdx], e, rangeSt, rangeEd);
            extra[curIdx] = extraMerger.merge(extra[curIdx], e);
        } else {
            pushDown(curIdx, rangeSt, rangeEd);
            int mid = rangeSt + (rangeEd - rangeSt) / 2;
            int leftIdx = leftChild(curIdx), rightIdx = rightChild(curIdx);
            updateRange(leftIdx, rangeSt, mid, updateSt, updateEd, e);
            updateRange(rightIdx, mid + 1, rangeEd, updateSt, updateEd, e);
            tree[curIdx] = function.merge(tree[leftIdx], tree[rightIdx]);
        }
    }

    private void pushDown(int curIdx, int st, int ed) {
        if (extra[curIdx] != null && extraMerger != null && extraUpdater != null) {
            int leftIdx = leftChild(curIdx), rightIdx = rightChild(curIdx);
            int mid = st + (ed - st) / 2;
            E e = extra[curIdx];
            tree[leftIdx] = extraUpdater.update(tree[leftIdx], e, st, mid);
            tree[rightIdx] = extraUpdater.update(tree[rightIdx], e, mid + 1, ed);
            extra[leftIdx] = extraMerger.merge(extra[leftIdx], e);
            extra[rightIdx] = extraMerger.merge(extra[rightIdx], e);
            extra[curIdx] = null;
        }
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append('[');
        for(int i = 0 ; i < tree.length ; i ++){
            if(tree[i] != null)
                res.append(tree[i]);
            else
                res.append("null");

            if(i != tree.length - 1)
                res.append(", ");
        }
        res.append(']');
        return res.toString() + "\n" + Arrays.toString(extra);
    }

}

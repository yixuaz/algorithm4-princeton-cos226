package part1.week5.bst;

public interface BinarySearchTree<Key extends Comparable<Key>, Val> {
    int size();
    boolean isEmpty();
    boolean contains(Key key);
    Val find(Key key);
    void put(Key key, Val val);
    boolean remove(Key key);
    Key select(int rank);
    int rank(Key key);
    Key floor(Key key);
    Key ceil(Key key);
}

package part1.week5.geoapp;

public interface IntervalSearchTree<Key extends Comparable<Key>, Value> {
    int size();
    void put(Key lo, Key hi, Value val);
    Value get(Key lo, Key hi);
    Value delete(Key lo, Key hi);
    Iterable<Value> intersects(Key lo, Key hi);
    boolean check();
}

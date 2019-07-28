package commonutil;

public interface IMerger<T,E> {
    E merge(T[] arr,int lo, int mid, int hi);
}

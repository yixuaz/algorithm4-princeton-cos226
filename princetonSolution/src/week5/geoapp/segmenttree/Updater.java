package week5.geoapp.segmenttree;

public interface Updater<E> {
    E update(E ori, E extra, int rangeSt, int rangeEd);
}

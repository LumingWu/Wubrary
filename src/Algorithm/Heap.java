package Algorithm;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A simple Minimum-Heap
 *
 * @author Luming
 */
public class Heap<T extends Comparable<T>> {

    private ArrayList<T> _heap;
    private int _heapsize;

    public Heap() {
        _heap = new ArrayList<T>();
        _heapsize = 0;
    }

    private void minHeapify(int index) {
        int l = getLeft(index);
        int r = getRight(index);
        int smallest;
        if (l < _heapsize && _heap.get(l).compareTo(_heap.get(index)) <= 0) {
            smallest = l;
        } else {
            smallest = index;
        }
        if (r < _heapsize && _heap.get(r).compareTo(_heap.get(smallest)) <= 0) {
            smallest = r;
        }
        if (smallest != index) {
            exchange(index, smallest);
            minHeapify(smallest);
        }
    }

    public void insert(T item) {
        _heap.add(item);
        _heapsize = _heapsize + 1;
        decreaseKey(_heapsize - 1);
    }

    private void decreaseKey(int index) {
        int i = index;
        while (i > 0 && _heap.get(getParent(i)).compareTo(_heap.get(i)) >= 0) {
            exchange(i, getParent(i));
            i = getParent(i);
        }
    }

    public T extract() {
        T result = _heap.get(0);
        exchange(0, _heapsize - 1);
        _heap.remove(_heapsize - 1);
        _heapsize = _heapsize - 1;
        if (_heapsize > 1) {
            minHeapify(0);
        }
        return result;
    }

    public void clear() {
        _heap.clear();
        _heapsize = 0;
    }

    public int size() {
        return _heapsize;
    }

    private void exchange(int index1, int index2) {
        T temp = _heap.get(index1);
        _heap.set(index1, _heap.get(index2));
        _heap.set(index2, temp);
    }

    private int getParent(int index) {
        return index / 2;
    }

    private int getLeft(int index) {
        if (index == 0) {
            return 1;
        }
        return 2 * index;
    }

    private int getRight(int index) {
        if (index == 0) {
            return 2;
        }
        return 2 * index + 1;
    }

    public String toString() {
        return _heap.toString();
    }
}

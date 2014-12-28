package Algorithm;

import java.util.ArrayList;

/**
 * A simple class that helps sort with Algorithm and learn Algorithm. Private-
 * Inner method or useless sorts.
 *
 * @author Luming
 */
public class Sorter<T extends Comparable<T>> {

    private int _heapsize;

    public Sorter() {

    }

    //ArrayList base
    /**
     * Selection Sort- Space: Small. Time: Stable O( n*n )
     */
    private void selectionSort(ArrayList<T> list) {
        int n = list.size();
        int smallest;
        for (int i = 0; i < n - 1; i++) {
            smallest = i;
            for (int j = i + 1; j < n; j++) {
                if (list.get(j).compareTo(list.get(smallest)) < 0) {
                    smallest = j;
                }
            }
            exchange(list, i, smallest);
        }
    }

    /**
     * Bubble Sort- Space: Small. Time: Stable O( n*n )
     */
    private void bubbleSort(ArrayList<T> list) {
        int n = list.size();
        int j;
        for (int i = 0; i < n; i++) {
            for (j = 0; j < n - i - 1; j++) {
                if (list.get(j).compareTo(list.get(j + 1)) > 0) {
                    exchange(list, j, j + 1);
                }
            }
        }
    }

    /**
     * Insertion Sort- Space: Small. Time: Worst O( n*n ) Best O(n) Use it when
     * list is partially sorted.
     */
    public void insertionSort(ArrayList<T> list) {
        int index;
        for (int i = 1; i < list.size(); i++) {
            index = i;
            while (list.get(index).compareTo(list.get(index - 1)) < 0) {
                exchange(list, index, index - 1);
                index = index - 1;
                if (index == 0) {
                    break;
                }
            }
        }
    }

    /**
     * Shell Sort- Space: Large. Time: Best O( 2n ln(n) ) Worst O( n^(3/2) )
     */
    private void shellSort(ArrayList<T> list) {
        int n = list.size();
        int i;
        int j;
        T temp;
        for (int increment = n; increment > 0; increment = increment / 2) {
            for (i = increment; i < n; i++) {
                temp = list.get(i);
                j = i;
                while (j >= increment && list.get(j - increment).compareTo(temp) > 0) {
                    list.set(j, list.get(j - increment));
                    j = j - increment;
                }
                list.set(j, temp);
            }
        }
    }

    /**
     * Merge Sort- Space: Large. Time: Stable O( n log(n) ) Use it when you want
     * the time cost to be stable and you can afford space.
     *
     * @return list
     */
    public ArrayList<T> mergeSort(ArrayList<T> list) {
        int n = list.size();
        if (n == 1) {
            return list;
        }
        int i;
        ArrayList<T> left = new ArrayList<T>();
        for (i = 0; i < n / 2; i++) {
            left.add(list.get(i));
        }
        ArrayList<T> right = new ArrayList<T>();
        for (i = n / 2; i < n; i++) {
            right.add(list.get(i));
        }
        return merge(mergeSort(left, n / 2), mergeSort(right, n - n / 2));
    }

    private ArrayList<T> mergeSort(ArrayList<T> list, int n) {
        if (n == 1) {
            return list;
        }
        int i;
        ArrayList<T> left = new ArrayList<T>();
        for (i = 0; i < n / 2; i++) {
            left.add(list.get(i));
        }
        ArrayList<T> right = new ArrayList<T>();
        for (i = n / 2; i < n; i++) {
            right.add(list.get(i));
        }
        return merge(mergeSort(left, n / 2), mergeSort(right, n - n / 2));
    }

    private ArrayList<T> merge(ArrayList<T> left, ArrayList<T> right) {
        ArrayList<T> result = new ArrayList<T>();
        int l = 0;
        int lsize = left.size();
        int r = 0;
        int rsize = right.size();
        while (l < lsize || r < rsize) {
            if (left.get(l).compareTo(right.get(r)) <= 0) {
                result.add(left.get(l));
                l = l + 1;
            } else {
                result.add(right.get(r));
                r = r + 1;
            }
            if (l == lsize) {
                while (r < rsize) {
                    result.add(right.get(r));
                    r = r + 1;
                }
            }
            if (r == rsize) {
                while (l < lsize) {
                    result.add(left.get(l));
                    l = l + 1;
                }
            }
        }
        return result;
    }

    /**
     * Quick Sort- Space: Small. Time: Best O( n log(n) ) Worst O( n*n ) Use it
     * when you want to save space and still want a decent n log (n) sort.
     */
    public void quickSort(ArrayList<T> list) {
        int n = list.size();
        int pivot = partition(list, 0, n - 1);
        quickSort(list, 0, pivot - 1);
        quickSort(list, pivot + 1, n - 1);
    }

    private void quickSort(ArrayList<T> list, int left, int right) {
        if (left < right) {
            int pivot = partition(list, left, right);
            quickSort(list, left, pivot - 1);
            quickSort(list, pivot + 1, right);
        }
    }

    private int partition(ArrayList<T> list, int left, int right) {
        int index = left - 1;
        T pivot = list.get(right);
        for (int i = left; i < right; i++) {
            if (list.get(i).compareTo(pivot) <= 0) {
                index = index + 1;
                exchange(list, index, i);
            }
        }
        exchange(list, index + 1, right);
        return index + 1;
    }

    /**
     * HeapSort Sort- Space: Small. Time: Worst O( n log(n) )
     */
    public void heapSort(ArrayList<T> list) {
        int n = list.size() - 1;
        buildMinHeap(list);
        for (int i = n; i >= 0; i--) {
            exchange(list, 0, i);
            _heapsize = _heapsize - 1;
            minHeapify(list, 0);
        }
    }

    private void buildMinHeap(ArrayList<T> list) {
        _heapsize = list.size();
        int n = _heapsize;
        for (int i = n / 2; i >= 0; i--) {
            minHeapify(list, i);
        }
    }

    private void minHeapify(ArrayList<T> list, int index) {
        int l = getLeft(index);
        int r = getRight(index);
        int smallest;
        if (l < _heapsize && list.get(l).compareTo(list.get(index)) > 0) {
            smallest = l;
        } else {
            smallest = index;
        }
        if (r < _heapsize && list.get(r).compareTo(list.get(smallest)) > 0) {
            smallest = r;
        }
        if (smallest != index) {
            exchange(list, index, smallest);
            minHeapify(list, smallest);
        }
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

    private void exchange(ArrayList<T> list, int index1, int index2) {
        T temp = list.get(index1);
        list.set(index1, list.get(index2));
        list.set(index2, temp);
    }

}

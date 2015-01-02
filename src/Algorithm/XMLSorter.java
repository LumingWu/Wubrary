package Algorithm;

import java.util.ArrayList;

/**
 * Just created to sort XML data structure.
 * @author LuLu
 */
public class XMLSorter {
    
    private int _heapsize;
    
    public XMLSorter(){
        
    }
    public void insertionSort(ArrayList<ArrayList<String>> list) {
        int index;
        for (int i = 1; i < list.size(); i++) {
            index = i;
            while (list.get(index).get(0).compareTo(list.get(index - 1).get(0)) < 0) {
                exchange(list, index, index - 1);
                index = index - 1;
                if (index == 0) {
                    break;
                }
            }
        }
    }
    
    public ArrayList<ArrayList<String>> mergeSort(ArrayList<ArrayList<String>> list) {
        int n = list.size();
        if (n == 1) {
            return list;
        }
        int i;
        ArrayList<ArrayList<String>> left = new ArrayList<ArrayList<String>>();
        for (i = 0; i < n / 2; i++) {
            left.add(list.get(i));
        }
        ArrayList<ArrayList<String>> right = new ArrayList<ArrayList<String>>();
        for (i = n / 2; i < n; i++) {
            right.add(list.get(i));
        }
        return merge(mergeSort(left, n / 2), mergeSort(right, n - n / 2));
    }

    private ArrayList<ArrayList<String>> mergeSort(ArrayList<ArrayList<String>> list, int n) {
        if (n == 1) {
            return list;
        }
        int i;
        ArrayList<ArrayList<String>> left = new ArrayList<ArrayList<String>>();
        for (i = 0; i < n / 2; i++) {
            left.add(list.get(i));
        }
        ArrayList<ArrayList<String>> right = new ArrayList<ArrayList<String>>();
        for (i = n / 2; i < n; i++) {
            right.add(list.get(i));
        }
        return merge(mergeSort(left, n / 2), mergeSort(right, n - n / 2));
    }

    private ArrayList<ArrayList<String>> merge(ArrayList<ArrayList<String>> left, ArrayList<ArrayList<String>> right) {
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        int l = 0;
        int lsize = left.size();
        int r = 0;
        int rsize = right.size();
        while (l < lsize || r < rsize) {
            if (left.get(l).get(0).compareTo(right.get(r).get(0)) <= 0) {
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
    
    public void quickSort(ArrayList<ArrayList<String>> list) {
        int n = list.size();
        int pivot = partition(list, 0, n - 1);
        quickSort(list, 0, pivot - 1);
        quickSort(list, pivot + 1, n - 1);
    }

    private void quickSort(ArrayList<ArrayList<String>> list, int left, int right) {
        if (left < right) {
            int pivot = partition(list, left, right);
            quickSort(list, left, pivot - 1);
            quickSort(list, pivot + 1, right);
        }
    }

    private int partition(ArrayList<ArrayList<String>> list, int left, int right) {
        int index = left - 1;
        String pivot = list.get(right).get(0);
        for (int i = left; i < right; i++) {
            if (list.get(i).get(0).compareTo(pivot) <= 0) {
                index = index + 1;
                exchange(list, index, i);
            }
        }
        exchange(list, index + 1, right);
        return index + 1;
    }
    
    public void heapSort(ArrayList<ArrayList<String>> list) {
        int n = list.size() - 1;
        buildMinHeap(list);
        for (int i = n; i >= 0; i--) {
            exchange(list, 0, i);
            _heapsize = _heapsize - 1;
            minHeapify(list, 0);
        }
    }

    private void buildMinHeap(ArrayList<ArrayList<String>> list) {
        _heapsize = list.size();
        int n = _heapsize;
        for (int i = n / 2; i >= 0; i--) {
            minHeapify(list, i);
        }
    }

    private void minHeapify(ArrayList<ArrayList<String>> list, int index) {
        int l = getLeft(index);
        int r = getRight(index);
        int smallest;
        if (l < _heapsize && list.get(l).get(0).compareTo(list.get(index).get(0)) > 0) {
            smallest = l;
        } else {
            smallest = index;
        }
        if (r < _heapsize && list.get(r).get(0).compareTo(list.get(smallest).get(0)) > 0) {
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

    private void exchange(ArrayList<ArrayList<String>> list, int index1, int index2) {
        ArrayList<String> temp = list.get(index1);
        list.set(index1, list.get(index2));
        list.set(index2, temp);
    }
}

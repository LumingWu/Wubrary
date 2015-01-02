/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithm;

import java.util.ArrayList;

/**
 *
 * @author LuLu
 */
public class Finder<T extends Comparable> {

    public Finder() {

    }

    public int BinarySearch(ArrayList<T> list, T item) {
        int left = 0;
        int right = list.size() - 1;
        int middle = -1;
        while (left <= right) {
            if (middle == (left + right) / 2) {
                return middle;
            } else {
                middle = (left + right) / 2;
            }
            if (list.get(middle).compareTo(item) == 0) {
                return middle;
            }
            if (list.get(middle).compareTo(item) > 0) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return middle;
    }

    public ArrayList<Integer> BinarySearchArea(ArrayList<T> list, T item) {
        int index = BinarySearch(list, item);
        ArrayList<Integer> result = new ArrayList<Integer>();
        result.add(index);
        int left = index;
        while (list.get(left - 1).compareTo(item) == 0) {
            left = left - 1;
            result.add(left);
        }
        int right = index;
        while (list.get(right + 1).compareTo(item) == 0) {
            right = right + 1;
            result.add(right);
        }
        return result;
    }
}

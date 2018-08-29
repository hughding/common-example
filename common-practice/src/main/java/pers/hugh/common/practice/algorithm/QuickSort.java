package pers.hugh.common.practice.algorithm;

import java.util.Arrays;

/**
 * @author xzding
 * @date 2018/8/29
 */
public class QuickSort {

    private static void partition(int[] a, int left, int right) {
        if (left > right) {
            return;
        }

        int i = left;
        int j = right;
        //基准数
        int key = a[left];
        int temp;

        while (i < j) {
            while (a[j] > key && j > i) {
                j--;
            }
            while (a[i] <= key && i < j) {
                i++;
            }

            if (i < j) {
                temp = a[j];
                a[j] = a[i];
                a[i] = temp;
            }
        }

        a[left] = a[i];
        a[i] = key;

        partition(a, left, i - 1);
        partition(a, i + 1, right);

    }

    public static void quickSort(int[] a) {
        partition(a, 0, a.length - 1);
    }

    public static void main(String[] args) {
        int[] a = new int[]{6, 1, 2, 7, 9, 3, 4, 5, 10, 8};
        quickSort(a);
        System.out.println(Arrays.toString(a));

        int[] b = new int[]{6, 3, 3, 6, 5, 6, 5, 3, 5, 6};
        quickSort(b);
        System.out.println(Arrays.toString(b));
    }

}

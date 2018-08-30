package pers.hugh.common.practice.algorithm;

import java.util.Arrays;

/**
 * @author xzding
 * @date 2018/8/30
 */
public class BubbleSort {

    /**
     * 向前冒泡
     *
     * @param a
     */
    public static void bubbleSortForward(int[] a) {
        int n = a.length;
        int temp;
        boolean swap = false;
        for (int i = 0; i < n; i++) {
            for (int j = n - 1; j > i; j--) {
                if (a[j] < a[j - 1]) {
                    temp = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temp;
                    swap = true;
                }
            }
            if (!swap) {
                break;
            }
        }
    }

    /**
     * 向后冒泡
     *
     * @param a
     */
    public static void bubbleSortBackward(int[] a) {
        int n = a.length;
        int temp;
        boolean swap = false;
        for (int i = n - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (a[j] > a[j + 1]) {
                    temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                    swap = true;
                }
            }
            if (!swap) {
                break;
            }
        }

    }


    public static void main(String[] args) {
        int[] a = new int[]{6, 1, 2, 7, 9, 3, 4, 5, 10, 8};
        bubbleSortForward(a);
        System.out.println(Arrays.toString(a));

        int[] b = new int[]{6, 3, 3, 6, 5, 6, 5, 3, 5, 6};
        bubbleSortForward(b);
        System.out.println(Arrays.toString(b));

        //有序数组
        bubbleSortForward(a);
        System.out.println(Arrays.toString(a));

        int[] c = new int[]{6, 1, 2, 7, 9, 3, 4, 5, 10, 8};
        bubbleSortBackward(c);
        System.out.println(Arrays.toString(c));

        int[] d = new int[]{6, 3, 3, 6, 5, 6, 5, 3, 5, 6};
        bubbleSortBackward(d);
        System.out.println(Arrays.toString(d));

        //有序数组
        bubbleSortBackward(c);
        System.out.println(Arrays.toString(c));
    }
}

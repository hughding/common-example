package pers.hugh.leetcode;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2017/12/4</pre>
 */
public class MedianOfTwoSortedArrays {
//    4. Median of Two Sorted Arrays
//    There are two sorted arrays nums1 and nums2 of size m and n respectively.
//
//    Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
//
//    Example 1:
//
//    nums1 = [1, 3]
//    nums2 = [2]
//
//    The median is 2.0
//
//    Example 2:
//
//    nums1 = [1, 2]
//    nums2 = [3, 4]
//
//    The median is (2 + 3)/2 = 2.5

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] nums = new int[nums1.length + nums2.length];
        int index1 = 0, index2 = 0;
        for (int i = 0; i < nums.length; i++) {
            if (index1 < nums1.length && index2 < nums2.length) {
                nums[i] = nums1[index1] < nums2[index2] ? nums1[index1++] : nums2[index2++];
            } else if (index1 < nums1.length) {
                nums[i] = nums1[index1++];
            } else if (index2 < nums2.length) {
                nums[i] = nums2[index2++];
            }
        }
        if (nums.length % 2 == 1) {
            return nums[nums.length / 2];
        } else {
            return (nums[nums.length / 2 - 1] + nums[nums.length / 2])/2.0;
        }
    }

    public static void main(String[] args) {
        MedianOfTwoSortedArrays solution = new MedianOfTwoSortedArrays();
        System.out.println(solution.findMedianSortedArrays(new int[]{1, 3}, new int[]{2}));
        System.out.println(solution.findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4}));
    }
}

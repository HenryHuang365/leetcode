// Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays.

// The overall run time complexity should be O(log (m+n)).

// Example 1:

// Input: nums1 = [1,3], nums2 = [2]
// Output: 2.00000
// Explanation: merged array = [1,2,3] and median is 2.
// Example 2:

// Input: nums1 = [1,2], nums2 = [3,4]
// Output: 2.50000
// Explanation: merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5.

// Constraints:

// nums1.length == m
// nums2.length == n
// 0 <= m <= 1000
// 0 <= n <= 1000
// 1 <= m + n <= 2000
// -106 <= nums1[i], nums2[i] <= 106

class Solution {
    // public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    // int m = nums1.length;
    // int n = nums2.length;
    // int len = m + n;
    // if (len % 2 == 0) {
    // double left = (double) findKthHelper(nums1, 0, nums2, 0, len / 2);
    // double right = (double) findKthHelper(nums1, 0, nums2, 0, len / 2 + 1);
    // return (double) (left + right) / 2;
    // } else {
    // return findKthHelper(nums1, 0, nums2, 0, len / 2 + 1);
    // }
    // }

    // public int findKthHelper(int[] A, int aStart, int[] B, int bStart, int k) {
    // if (aStart >= A.length) {
    // return B[bStart + k - 1];
    // }

    // if (bStart >= B.length) {
    // return A[aStart + k - 1];
    // }

    // if (k == 1) {
    // return Math.min(A[aStart], B[bStart]);
    // }

    // int aMid = aStart + k / 2 - 1;
    // int bMid = bStart + k / 2 - 1;
    // int aVal = aMid >= A.length ? Integer.MAX_VALUE : A[aMid];
    // int bVal = bMid >= B.length ? Integer.MAX_VALUE : B[bMid];

    // if (aVal <= bVal) {
    // return findKthHelper(A, aMid + 1, B, bStart, k - k / 2);
    // } else {
    // return findKthHelper(A, aStart, B, bMid + 1, k - k / 2);
    // }
    // }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n1 = nums1.length;
        int n2 = nums2.length;
        if (n1 > n2) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int k = (n1 + n2 + 1) / 2;

        int l = 0;
        int r = n1;

        while (l < r) {
            int m1 = l + (r - l) / 2;
            int m2 = k - m1;
            if (nums1[m1] < nums2[m2 - 1]) {
                l = m1 + 1;
            } else {
                r = m1;
            }
        }

        int m1 = l;
        int m2 = k - l;

        int c1 = Math.max(m1 <= 0 ? Integer.MIN_VALUE : nums1[m1 - 1],
                m2 <= 0 ? Integer.MIN_VALUE : nums2[m2 - 1]);

        if ((n1 + n2) % 2 == 1) {
            return c1;
        }

        int c2 = Math.min(m1 >= n1 ? Integer.MAX_VALUE : nums1[m1],
                m2 >= n2 ? Integer.MAX_VALUE : nums2[m2]);

        return (c1 + c2) * 0.5;
    }
}
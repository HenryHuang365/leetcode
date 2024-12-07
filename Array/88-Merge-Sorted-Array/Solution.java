import java.util.Arrays;

public class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        for (int i = 0; i < n; i++) {
            nums1[i + m] = nums2[i];
        }
        Arrays.sort(nums1);

        System.out.println("num1: " + Arrays.toString(nums1));
    }

    public void mergeMethodTwo(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;
        int j = n - 1;
        int k = m + n - 1;

        while (j >= 0) {
            if (i >= 0 && nums1[i] > nums2[j]) {
                nums1[k] = nums1[i];
                k--;
                i--;
            } else {
                nums1[k] = nums2[j];
                k--;
                j--;
            }
        }

        System.out.println("num1: " + Arrays.toString(nums1));
    }
    
    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums1 = new int[] {1,2,3,0,0,0};
        int[] nums2 = new int[] {2,5,6};
        solution.merge(nums1, 3, nums2, 3);

        int[] nums3 = new int[] {1,2,3,0,0,0};
        int[] nums4 = new int[] {2,5,6};
        solution.mergeMethodTwo(nums3, 3, nums4, 3);
    }
}
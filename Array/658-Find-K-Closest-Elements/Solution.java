import java.util.*;

/**
 * Solution
 */
class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        List<Integer> result = new ArrayList<>();

        int left = 0, right = arr.length - k;

        while (left < right) {
            int mid = left + (right - left) / 2;
            // System.out.println("left: " + left);
            // System.out.println("right: " + right);
            // System.out.println("mid: " + mid);
            if (x - arr[mid] > arr[mid + k] - x) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        for (int i = left; i < left + k; i++) {
            result.add(arr[i]);
        }

        return result;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] input = new int[] { 1, 2, 3, 4, 5 };
        System.out.println("Output: " + solution.findClosestElements(input, 4, 3));
    }
}
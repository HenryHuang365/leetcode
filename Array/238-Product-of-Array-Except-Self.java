// Given an integer array nums, return an array answer such that answer[i] is
// equal to the product of all the elements of nums except nums[i].

// The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit
// integer.

// You must write an algorithm that runs in O(n) time and without using the
// division operation.

// Example 1:

// Input: nums = [1,2,3,4]
// Output: [24,12,8,6]
// Example 2:

// Input: nums = [-1,1,0,-3,3]
// Output: [0,0,9,0,0]

// Constraints:

// 2 <= nums.length <= 105
// -30 <= nums[i] <= 30
// The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit
// integer.

// Follow up: Can you solve the problem in O(1) extra space complexity? (The
// output array does not count as extra space for space complexity analysis.)
import java.util.List;
import java.util.ArrayList;

class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] list = new int[n];

        List<Integer> left_product = new ArrayList<>();
        List<Integer> right_product = new ArrayList<>();
        int product1 = 1;
        for (int i = 0; i < n; i++) {
            product1 = product1 * nums[i];
            left_product.add(product1);
        }

        int product2 = 1;
        for (int j = n - 1; j >= 0; j--) {
            product2 = product2 * nums[j];
            right_product.add(product2);
        }

        System.out.println("left product: " + left_product);
        System.out.println("right product: " + right_product);

        for (int k = 0; k < n; k++) {
            int left_index = k - 1;
            int right_index = n - k - 2;

            int left_index_ele = left_index >= 0 ? left_product.get(left_index) : 1;
            int right_index_ele = right_index >= 0 ? right_product.get(right_index) : 1;
            System.out.println("left_index_ele: " + left_index_ele);
            System.out.println("right_index_ele: " + right_index_ele);

            int product = left_index_ele * right_index_ele;
            list[k] = product;
        }

        return list;
    }
}
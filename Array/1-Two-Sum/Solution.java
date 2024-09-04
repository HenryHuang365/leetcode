import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
class Solution {
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[] { i, j };
                }
            }
        }
        return new int[] {};
    }

    public int[] twoSumMap(int[] nums, int target) {
        Map<Integer, Integer> valueIndex = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            valueIndex.put(nums[i], i);
        }
        
        for (int i = 0; i < nums.length; i++) {
            int dif = target - nums[i];
            if (valueIndex.containsKey(dif) && valueIndex.get(dif) != i) {
                return new int[]{i, valueIndex.get(dif)};
            }
        }
        return new int[]{};
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] input = new int[]{2,7,11,15};
        int[] input1 = new int[]{3, 3};
        int[] input2 = new int[]{3,2,4};

        System.out.println("Output: " + Arrays.toString(solution.twoSum(input, 9)));
        System.out.println("Output: " + Arrays.toString(solution.twoSumMap(input1, 6)));
        System.out.println("Output: " + Arrays.toString(solution.twoSumMap(input2, 6)));
    }
}

// Learning points:
// 1. Java initialise array of intergers:
// myArray = new int[]{1, 2, 3, 4}
// int[] myArray = {1, 2 ,3, 4}
// int myArray[] = {1, 2, 3, 4}
// int myArray = new int[4]
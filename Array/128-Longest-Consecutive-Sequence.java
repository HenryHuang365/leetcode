// Given an unsorted array of integers nums, return the length of the longest
// consecutive elements sequence.

// You must write an algorithm that runs in O(n) time.

// Example 1:

// Input: nums = [100,4,200,1,3,2]
// Output: 4
// Explanation: The longest consecutive elements sequence is [1, 2, 3, 4].
// Therefore its length is 4.
// Example 2:

// Input: nums = [0,3,7,2,5,8,4,6,0,1]
// Output: 9

// Constraints:

// 0 <= nums.length <= 105
// -109 <= nums[i] <= 109
import java.util.HashSet;

class Solution {
    public int longestConsecutive(int[] nums) {
        int longestSequence = 0;
        HashSet<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            if (!numSet.contains(num)) {
                numSet.add(num);
            }
        }

        System.out.println("numSet: " + numSet);

        for (int num : nums) {

            if (!numSet.contains(num - 1)) {
                int length = 0;
                while (numSet.contains(num++)) {
                    length++;
                }

                longestSequence = Math.max(longestSequence, length);
            }
        }

        return longestSequence;
    }
}